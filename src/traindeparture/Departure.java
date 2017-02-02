package traindeparture;

/**
 *
 * @version 31.10.20164
 * @author Daniel Peters
 */
public class Departure implements Comparable<Departure> {

    /**
     * 
     */
    private String trainNo;
    
    /**
     * 
     */
    private String departureTime;
    
    /**
     * 
     */
    private String destination;
    
    /**
     * 
     */
    private String via;
    
    /**
     * 
     */
    private String platform;

    /**
     * Default constructor.
     * Initializes all object attributes
     * 
     * @param trainNo initial train number
     * @param departureTime initial departure time
     * @param destination initial destination
     * @param via inital via
     * @param platform initial platform
     */
    public Departure(String trainNo, String departureTime, String destination, String via, String platform) {
        this.trainNo = trainNo;
        this.departureTime = departureTime;
        this.destination = destination;
        this.via = via;
        this.platform = platform;
    }

    /* Getters and setters for attributes */
    
    public String getTrainNo() {
        return trainNo;
    }

    /**
     * 
     * @param trainNo 
     */
    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    /**
     * 
     * @return 
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Getter for departure time
     * 
     * @param departureTime 
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Getter for destination
     * 
     * @return 
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Setter for destination
     * 
     * @param destination 
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Getter vor the Stations passed
     * 
     * @return 
     */
    public String getVia() {
        return via;
    }

    /**
     * 
     * @param via 
     */
    public void setVia(String via) {
        this.via = via;
    }

    /**
     * 
     * @return 
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 
     * @param platform 
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    
    /* Methods */

    /**
     * Display all attributes of this object
     */
    public void showAllInfos() {
        String departure = "Abfahrtinfo\n"
                + "Zug: " + this.trainNo + "\n"
                + "Abfahrt: " + this.departureTime + "\n"
                + "Ziel: " + this.destination + "\n"
                + "Via: " + this.via + "\n"
                + "Platform: " + this.platform + "\n";
        System.out.println(departure);
    }
    
    /**
     * Checks if the specified key is in the value of an attribute
     * @param attribVal the attribute value
     * @param key user specified key
     * @return boolean which tells wether the attribute value contains the key
     */
    public boolean has(String attribVal, String key) {
        boolean foundIt = false;
        attribVal = attribVal.toLowerCase();
        key = key.toLowerCase();
        if (attribVal.contains(key)) {
            foundIt = true;
        }
        return foundIt;
    }
    
    /**
     * Check if this object has the specified platform
     * @param platform user specified platform
     * @return whether the Departure object has the specified platorm
     */
    public boolean hasPlatform(String platform){
        boolean in = has(this.platform, platform);
        return in;
    }
    
    /**
     * Check if this object has the specified destination
     * @param destination user specified destination
     * @return whether the Departure object has the specified destination
     */
    public boolean hasDestination(String destination){
        boolean in = has(this.destination, destination);
        return in;
    }
    
    /**
     * Check if this object has the specified via
     * @param via user specified via
     * @return whether the Departure object has the specified via
     */
    public boolean hasVia(String via){
        boolean in = has(this.via, via);
        return in;
    }

    /**
     * Compares the departure time of this Departure object to another
     * 
     * @param o Departure object to be compared to
     * @return integer for sorting purposes
     */
    @Override
    public int compareTo(Departure o) {
        return o.getDepartureTime().compareTo(this.departureTime);
    }
}
