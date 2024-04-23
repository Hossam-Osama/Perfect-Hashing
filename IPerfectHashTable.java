public interface IPerfectHashTable {
    public boolean insert(String key);
    public boolean delete(String key);
    public boolean search(String key); 
    public void batchInsert(String filePath); 
    public void batchDelete(String filePath) ;
    public int getTotalInserted();
    public int getTotalDeleted();
    public int getFailedDeleted();
    public int getfailedInserted();
    public int getnumberOfResh();
    public int getnumberOfelement();
}
