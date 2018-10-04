package handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CountDownLatch;

/**
 * run this main method and check result 
 */
public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
    public static void main(String args[]) throws Exception {
    	FabricHelper helper = FabricHelper.getInstance();
    	helper.setConfigPath("D:/PRECIOUS/IT/chaincode/huawei/day19/JavaSDKDemo-master/JavaSDKDemo-master/config/java-sdk-demo-sdk-config.yaml");
    	helper.setChannelName("channeldemo");
    	helper.setChaincodeName("chaincode");
    	helper.setAccessKey("ec25aea77f64d2137c8af50af3aecd66619040ed");
    	
    	LoopInvoke(1);
    }
    
    public static void LoopInvoke(int loop) throws Exception{
    	FabricHelper helper = FabricHelper.getInstance();
    	for (int i=0; i<loop;i++){
        	helper.invokeBlockchain("invoke", new String[]{"a","b","100"});
        	String a=helper.queryBlockchain("query", new String[]{"a"});
        	String b=helper.queryBlockchain("query", new String[]{"b"});
        	logger.info("after invoke  a=" + a +", invoke b=" + b);
    	}
    }
    
    //StartMultiTask(1, 1);
    public static void StartMultiTask(int threadNumber, int loop) throws InterruptedException{
         final CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
         for (int i = 0; i < threadNumber; i++) {
             final int threadID = i;
             new Thread() {
                 public void run() {
                     try {
                    	 LoopInvoke(loop);
                     } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                     logger.info("threadID:[%s] finished!!", threadID);
                     countDownLatch.countDown();
                 }
             }.start();
         }

         countDownLatch.await();
         logger.info("main thread finished!!");
    }
}


