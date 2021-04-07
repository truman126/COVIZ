package ca.group1.coviz.gui;

import ca.group1.coviz.analysisengine.AnalysisExecutor;
import ca.group1.coviz.analysisengine.analyses.AnalysisType;
import ca.group1.coviz.analysisengine.apiutils.InvalidAnalysisSelectionException;
import ca.group1.coviz.analysisengine.util.AnalysisResult;
import ca.group1.coviz.gui.analysisrendering.AnalysisResultRenderer;
import ca.group1.coviz.gui.analysisrendering.util.RenderResult;
import ca.group1.coviz.queryconstruction.CountryListBuilder;
import ca.group1.coviz.util.Country;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This class creates the GUI instance for the program
 * @extends Jframe
 */
public class CovizGUI extends JFrame {


    private final int mapWidth = 1024;
    private final int mapHeight = 615;


    //the main panel, to act as a frame
    private JPanel main = new JPanel();
    private ImageIcon map;


    //the map picture
    private JLabel labelPic = new JLabel();

    //the panels
    private JPanel topPanel = new JPanel();
    private JPanel mapPanel = new JPanel();
    private JPanel leftPanel = new JPanel();


    //the objects for country mgmt
    private JTextField addCountry = new JTextField(20);
    private JTextField removeCountry = new JTextField(20);
    private JButton addButton = new JButton("Add");
    private JButton removeButton = new JButton("Remove");
    private JLabel addTitle = new JLabel("Add a country: ");
    private JLabel removeTitle = new JLabel("Remove a country: ");

    //objects for the left panel
    private JComboBox selectAnalysisType;
    private JButton analyzeButton = new JButton("Analyze");
    private JLabel analysisTitle  = new JLabel("Select type of analysis");
    private JLabel countryListTitle  = new JLabel("Countries being analyzed: ");
    private JTextArea countryListDisplay = new JTextArea();
    private JLabel resultsTitle  = new JLabel("Results: ");
    private JTextArea resultsDisplay = new JTextArea();

    private AnalysisExecutor analysisExecutor;
    private AnalysisResultRenderer analysisResultRenderer;

    private CountryListBuilder countryList = CountryListBuilder.getInstance();

    /**
     *
     * @param lat
     * @param lng
     * @param mapWidth
     * @param mapHeight
     * @return The XY translation of the long, lat
     */
    private Point getXY(double lat, double lng, int mapWidth, int mapHeight) {

        int screenX = (int) Math.round((((lng + 180) / 360) * mapWidth));

        int screenY = (int) Math.round(((((lat * -1) + 90) / 180) * mapHeight));

        return new Point(screenX, screenY);

    }

    /**
     *
     * @return returns a string List of the country
     */
    private String getCountryList(){

        StringBuilder output= new StringBuilder();

        for (int i = 0; i < countryList.getList().size(); i++){
            String c = countryList.getList().get(i).getLongName();
            output.append(toProperCase(c)).append("\n");

        }
        return output.toString();
    }

    /**
     *
     * @param in takes in the string of a country name
     * @return  capitalizes the first letter of the country name
     */
    private String toProperCase(String in){
        String temp = in.substring(0,1).toUpperCase();
        return temp +  in.substring(1);
    }

    /**
     *
     * @param typeOfAnalysis takes in what the user selects from the drop down
     * @return returns the analysis results after the analysis is performed
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws InvalidAnalysisSelectionException
     * @throws IOException
     */
    private AnalysisResult performAnalysis(String typeOfAnalysis) throws InterruptedException, ExecutionException, InvalidAnalysisSelectionException, IOException {
        AnalysisType analysisType = AnalysisType.fromString(typeOfAnalysis);
        if(analysisType==null){
            throw new InvalidAnalysisSelectionException("Unknown Analysis Type Selected");
        }
        System.out.println(typeOfAnalysis);
        analysisExecutor.setAnalysisType(analysisType);
        List<Country> targets = countryList.getList();
        if(targets.size() > 0) {
            return analysisExecutor.runAnalysis(targets);
        }
        return null;

    }


    /**
     * resets the map image every time a new analysis is displayed
     */
    private void resetImage(){

        map = new ImageIcon("map.jpg");
        Image image = map.getImage(); // transform it
        Image scaledImage = image.getScaledInstance(mapWidth,mapHeight,Image.SCALE_SMOOTH);
        map = new ImageIcon(scaledImage);  // transform it back

    }

    /**
     * this method constructs the gui instance
     * @param title this is the title of the GUI window
     */
    public CovizGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(main);
        this.pack();
        this.setSize(1440,960);

        // the analysis executor types.
        // Useful methods:
        // analysisExecutor.setAnalysisType(AnalysisType t)
        // analysisExecutor.getAnalysisType() -> AnalysisType
        // analysisExecutor.runAnalysis(List<Country> targets) -> AnalysisResult
        // analysisExecutor.getResults() -> AnalysisResult
        // analysisExecutor.hasResult() -> boolean
        // analysisExecutor.canAnalyze() -> boolean
        this.analysisExecutor = new AnalysisExecutor();

        // analysisResultRenderer.render() -> RenderResult !requires an analysis to be successfully completed
        // analysisResultRenderer.canRender() -> boolean
        this.analysisResultRenderer = new AnalysisResultRenderer(this.analysisExecutor);


        main.setLayout(new BorderLayout(8,6));
        topPanel.setLayout(new FlowLayout(2));



        map = new ImageIcon("map.jpg");
        Image image = map.getImage(); // transform it
        Image scaledImage = image.getScaledInstance(mapWidth,mapHeight,Image.SCALE_SMOOTH);
        map = new ImageIcon(scaledImage);  // transform it back
        labelPic.setIcon(map);
        mapPanel.add(labelPic);
        mapPanel.setSize( 1280, 644 );


        //drawing circles on the map
        Point target = getXY(56, 106,mapWidth, mapHeight);




        //add the add and remove country text boxes
        topPanel.add(addTitle);
        topPanel.add(addCountry);
        topPanel.add(addButton);
        topPanel.add(removeTitle);
        topPanel.add(removeCountry);
        topPanel.add(removeButton);


        //action listeners for adding and removing countries
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //takes the input from the text box
                String input = addCountry.getText();
                System.out.println(input);
                countryList.addCountry(input);

                String output = getCountryList();
                //clears the text box to avoid spamming
                addCountry.setText("");
                countryListDisplay.setText(output);
                addCountry.setText("");
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //takes the input from the text box
                String input = removeCountry.getText();
                countryList.removeCountry(countryList.getCountryByName(input));
                String output = getCountryList();


                //clears the text box to avoid spamming
                removeCountry.setText("");
                countryListDisplay.setText(output);
                removeCountry.setText("");
            }
        });


        //left panel
        JPanel analysisPanel = new JPanel();
        analysisPanel.setMinimumSize(new Dimension(450,150));
        JPanel countryPanel = new JPanel();
        countryPanel.setMinimumSize(new Dimension(450,300));
        JPanel resultPanel = new JPanel();
        resultPanel.setMinimumSize(new Dimension(450,500));

        //to add the scrolling feature for both text panels
        JPanel countryTextPanel = new JPanel();
        countryListDisplay = new JTextArea(16, 32);
        JScrollPane countryScroll = new JScrollPane(countryListDisplay);
        countryScroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        countryTextPanel.add(countryScroll);

        JPanel resultTextPanel = new JPanel();
        resultsDisplay = new JTextArea(16, 32);
        JScrollPane resultScroll = new JScrollPane(resultsDisplay);
        resultScroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        resultTextPanel.add(resultScroll);

        //GridLayout leftGrid = new GridLayout(3,1,5,5);
        BoxLayout leftBox = new BoxLayout(leftPanel, 1);

        leftPanel.setLayout(leftBox);
        countryPanel.setLayout(new GridLayout(2,1));
        resultPanel.setLayout(new GridLayout(2,1));



        String[] options = { null, "Cases per capita", "Deaths per capita", "Majority of cases per sex", "Total Cases"};
        selectAnalysisType = new JComboBox(options);
        selectAnalysisType.setSize(selectAnalysisType.getPreferredSize());

        countryListDisplay.setEditable(false);
        resultsDisplay.setEditable(false);


        analysisPanel.add(analysisTitle);
        analysisPanel.add(selectAnalysisType);
        analysisPanel.add(analyzeButton);

        countryPanel.add(countryListTitle);
        countryPanel.add(countryTextPanel);


        resultPanel.add(resultsTitle);
        resultPanel.add(resultTextPanel);

        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        leftPanel.add(analysisPanel);
        leftPanel.add(Box.createGlue());
        leftPanel.add(countryPanel);
        leftPanel.add(Box.createGlue());
        leftPanel.add(resultPanel);
        leftPanel.add(Box.createGlue());


        //event listener for when new analyses are performed
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //takes the input from the text box
                resetImage();
                if (selectAnalysisType.getSelectedItem() == null) resultsDisplay.setText("Please select an analysis type.");
                else{

                    String input = selectAnalysisType.getSelectedItem().toString();
                    StringBuilder resultsDisplayContent = new StringBuilder();


                    try {
                        //draw circles
                        AnalysisExecutor temp = new AnalysisExecutor();
                        AnalysisType type = AnalysisType.fromString(input);
                        temp.setAnalysisType(type);

                        temp.runAnalysis(countryList.getList());

                        AnalysisResultRenderer render = new AnalysisResultRenderer(temp);

                        RenderResult circleInfo = render.render();
                        System.out.println(circleInfo.getCircles());

                        Color transparent = new Color(0x00FFFFFF, true);
                        Image image = (Image) map.getImage();
                        BufferedImage bi = new BufferedImage(
                            map.getIconWidth(),
                            map.getIconHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                        Graphics g = bi.getGraphics();
                        g.setColor(Color.red);

                        bi.getGraphics().drawImage(image,0,0,null);
                        AnalysisResult result = performAnalysis(input);
                        if(result != null) {
                            for (Map.Entry<Country, Map<String, Double>> c : result.getRawData().entrySet()) {
                                resultsDisplayContent.append(toProperCase(c.getKey().getLongName())).append(":\n");
                                Point coords = getXY(c.getKey().getLatitude(),c.getKey().getLongitude(), mapWidth, mapHeight);

                                Double radius = circleInfo.getCircles().get(c.getKey()).getRadius();
                                int intRadius = (int) Math.round(radius);


                                Color circleColor = circleInfo.getCircles().get(c.getKey()).getColor();
                                g.setColor(circleColor);
                                g.fillOval(coords.x-intRadius,coords.y-intRadius,intRadius*2, intRadius*2);
                                for (Map.Entry<String, Double> s : c.getValue().entrySet()) { // print each entry on its own line but indentend
                                    resultsDisplayContent.append(String.format("\t%s: %f\n", s.getKey(), s.getValue()));
                                }
                            }
                            resultsDisplay.setText(resultsDisplayContent.toString());






                            map = new ImageIcon(bi);
                            labelPic.setIcon(map);





                        }else{
                            resultsDisplay.setText("Please add countries to analyze.");
                        }

                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    } catch (ExecutionException executionException) {
                        executionException.printStackTrace();
                    } catch (InvalidAnalysisSelectionException invalidAnalysisSelectionException) {

                        invalidAnalysisSelectionException.printStackTrace();

                        resultsDisplay.setText("Please select an analysis type");

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }


                }
            }
        });

        //add all the subpanels to the main panel


        //to show the scope of the panels if needed
//        leftPanel.setBackground(Color.blue);
//        topPanel.setBackground(Color.green);
//        mapPanel.setBackground(Color.red);


        main.add(topPanel, BorderLayout.NORTH);
        main.add(leftPanel, BorderLayout.WEST);
        main.add(mapPanel, BorderLayout.LINE_END);

    }

    /**
     * the main method
     * @param args
     */
    public static void main(String[] args){

        JFrame frame = new CovizGUI("COVIZ");

        frame.setVisible(true);
    }
}
