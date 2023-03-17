
public class ThreadMain extends Thread {
	IDMLogin idm;
     
    public ThreadMain(int num){
    	idm = new IDMLogin();
    }
    
    public void run(){
        for(int l = 0;l<1;l++){
           try{
        	   idm.Run();
           }catch(Exception exc){
               
           }
           
        }
    } 
}
