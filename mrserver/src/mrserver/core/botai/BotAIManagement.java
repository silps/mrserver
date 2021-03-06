package mrserver.core.botai;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import mrserver.core.Core;
import mrserver.core.botai.data.BotAI;
import mrserver.core.botai.network.BotAIConnections;
import mrserver.core.botai.network.BotAiHost;
import mrserver.core.botai.network.receive.Creator;
import mrserver.core.botai.network.receive.Receiver;
import mrserver.core.scenario.ScenarioManagement;
import mrserver.gui.options.interfaces.AIListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotAIManagement {
	
    private static BotAIManagement INSTANCE;

    private BotAIManagement(){
    	
    }
    
    public static BotAIManagement getInstance() {
        
        if( BotAIManagement.INSTANCE == null){
        	BotAIManagement.getLogger().debug( "Creating BotAIManagement-instance." );
        	BotAIManagement.INSTANCE = new BotAIManagement();
        }

        BotAIManagement.getLogger().trace( "Retrieving BotAIManagement-instance." );
        return BotAIManagement.INSTANCE;
        
    }
    
    private static Logger BOTAIMANAGEMENTLOGGER = LogManager.getLogger("BOTAIMANAGEMENT");
    
    public static Logger getLogger(){
        
        return BOTAIMANAGEMENTLOGGER;
        
    }

    private ConcurrentHashMap<SocketAddress, BotAI> mMapOfConnectedBotAIs = new ConcurrentHashMap<SocketAddress, BotAI>();;
    private BotAIConnections mBotAIsConnections = new BotAIConnections();
    private List<Receiver> mBotAIReceiver = new ArrayList<Receiver>();
    private Creator mBotAICreator;
	private AIListener mAIListener;
    
    public void startBotAIManagement(){
    	
    	BotAIManagement.getLogger().info( "Starting botaimanagement" );
    	
    	readBotsFromFile();
    	
    	startCreator();
    	
    }

	private void startCreator() {
		
		if( mBotAICreator == null || !mBotAICreator.isAlive() ){
		
			BotAIManagement.getLogger().debug( "Starting creator." );
	    	mBotAICreator= new Creator();
	    	mBotAICreator.startManagement();
		
		}
		
	}

	private void readBotsFromFile() {
		
		BotAIManagement.getLogger().debug( "Creating botaiconnections." );
    	mBotAIsConnections = new BotAIConnections( Core.getInstance().getServerConfig().getBotPorts() );

    	BotAIManagement.getLogger().debug( "Starting receiver." );
    	for( BotAiHost vBotAIHost : mBotAIsConnections.getListOfHosts() ){
    		
    		mBotAIReceiver.add( new Receiver( vBotAIHost ) );
    		
    	}
    	for( Receiver vBotAIReceiver : mBotAIReceiver ){
    		
    		vBotAIReceiver.startManagement();
    		BotAIManagement.getLogger().debug( "Started receiver for " + vBotAIReceiver.toString() );
    		
    	}
    	
	}
    
    public Receiver addBotAIPort( int aPort ){
    	
    	BotAiHost aHost = mBotAIsConnections.addBotAIPort( aPort );
    	if( aHost != null ) {
	    	Receiver vReceiver = new Receiver( aHost );
	    	vReceiver.startManagement();
	    	mBotAIReceiver.add( vReceiver );
	    	
	    	startCreator();
	    	
	    	return vReceiver;
    	}
    	return null;
    	
    }
    
    public void close(){
    	
    	if( mBotAIReceiver != null ){

        	for( Receiver vBotAIReceiver : mBotAIReceiver ){
        		
        		vBotAIReceiver.stopManagement();
        		BotAIManagement.getLogger().debug( "Stopped receiver for " + vBotAIReceiver.toString() );
        		
        	}
    	}
    	if( mBotAICreator != null ) {
    		mBotAICreator.stopManagement();
    	}
    	if( mBotAIsConnections != null ) {
    		mBotAIsConnections.closeConnection();
    	}
    	BotAIManagement.getLogger().info( "Botaimanagement closed" );
    	
    }
    
    public void removeReceiver( Receiver aReceiver ){
    	
    	mBotAIsConnections.getListOfHosts().remove( aReceiver.getBotAIConnect() );
    	mBotAIReceiver.remove( aReceiver );
    	aReceiver.close();
    	
    }
    
    public ConcurrentHashMap<SocketAddress, BotAI> getMapOfBotAIs(){
    	
    	BotAIManagement.getLogger().trace( "Retrieving mapofconnectedbotais" );
    	return mMapOfConnectedBotAIs;
    	
    }

	public List<Receiver> getBotAIReceiver() {
		return mBotAIReceiver;
	}
	
	public void unregisterBotAI( BotAI aAI ){
		
		ScenarioManagement.getInstance().unregisterBot( aAI );
		mMapOfConnectedBotAIs.remove( (SocketAddress) aAI.getSocketAddress() );
		
	}
	
	public void registerBotAIListener( AIListener aListener ){
		
		mAIListener = aListener;
		
	}

	public void putNewAI( BotAI aBotAI) {

		mMapOfConnectedBotAIs.put( (SocketAddress) aBotAI.getSocketAddress(), aBotAI );
		mAIListener.newAI( aBotAI );
		
	}
    
}
