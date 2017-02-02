package traindeparture;

import java.util.List;

/**
 *
 * @author d.peters
 */
public interface DataLoader {
    
    /**
     * 
     */
    public void load();
    
    /**
     * 
     * @return 
     */
    public List<Departure> loadList();
}
