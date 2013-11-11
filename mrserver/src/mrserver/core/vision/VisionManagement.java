package mrserver.core.vision;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrserver.core.Core;
import mrserver.core.vision.network.VisionConnection;
import mrservermisc.network.data.position.PositionDataPackage;
import mrservermisc.network.data.position.VisionMode;
import mrservermisc.network.data.visionmode.ChangeVisionMode;
import mrservermisc.vision.interfaces.Vision;

/**
 * Managed die Verbindung und den Datenaustausch zum Visionmodul
 * 
 * @author Eike Petersen
 * @since 0.1
 * @version 0.1
 */
public class VisionManagement implements Vision{
  
    private static VisionManagement INSTANCE;
    
    private VisionManagement(){
    	
    }

    public static VisionManagement getInstance() {
        
        if( VisionManagement.INSTANCE == null){
        	VisionManagement.getLogger().debug( "Creating VisionManagement-instance." );
        	VisionManagement.INSTANCE = new VisionManagement();
        }

        Core.getLogger().trace( "Retrieving VisionManagement-instance." );
        return VisionManagement.INSTANCE;
        
    }
    
    private static Logger VISIONMANAGEMENTLOGGER = LogManager.getLogger("VISIONMANAGEMENT");

    public static Logger getLogger(){
        
        return VISIONMANAGEMENTLOGGER;
        
    }
    
	private VisionConnection mVisionConnect;
	private VisionIncomingPacketsManagement mIncomingPacketManagement;
    
    public void close() {

        if( INSTANCE != null ){
        	
        	if( mVisionConnect != null ){

            	mVisionConnect.closeConnection();
            	mVisionConnect = null;
        		
        	}
        	
            INSTANCE = null;
            
        }
        
    }
    
    /**
     * Verbindet den Server zu einem Visionmodul 
     * 
     * @return true ob die Verbindung erfolgreich hergestellt werden konnte, fale wenn nicht
     */
    public boolean connectToVision(){
    	
    	try {
    		
    		mVisionConnect = new VisionConnection();
    		
    		if( mVisionConnect.establishConnection() ){
    			
    			mIncomingPacketManagement = new VisionIncomingPacketsManagement( mVisionConnect );
    			return mVisionConnect.isConnected();
    			
    		}
        
    	} catch ( Exception vException ) {

	        VisionManagement.getLogger().error( "Fehler beim initialisiern der visionconnection: " + vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
    	}
    	
    	return false;
    	
    }
    
    public boolean startRecievingPackets(){
    	
    	return mIncomingPacketManagement.startManagement();
    	
    }
    
    /**
     * Ändert den Modus des verbundenen Visionmodul 
     * 
     * @return true ob der Modus erfolgreich geändert werden konnte
     */
    public boolean changeVisionMode( VisionMode aVisionMode ){
    		
		try {
		
			VisionManagement.getLogger().debug( "Setting visionmode: " + aVisionMode );
			
			mIncomingPacketManagement.suspendManagement();
			mVisionConnect.sendDatagrammString( new ChangeVisionMode( aVisionMode ).toXMLString() );
			
			ChangeVisionMode vChangeAcknowlegement = ChangeVisionMode.unmarshallXMLChangeVisionModeString( mVisionConnect.getDatagrammString( 1000 ) );
			
			//TODO: in ein datapacket ändern
			VisionManagement.getLogger().debug( "Recieving visionmode acknowlegement: " + vChangeAcknowlegement );
			
			if( vChangeAcknowlegement != null && aVisionMode == vChangeAcknowlegement.getVisionMode() ){
				
				mIncomingPacketManagement.resumeManagement();
				VisionManagement.getLogger().info( "Set visionmode to " + aVisionMode );
				return true;
				
			}
	
		} catch ( Exception vException ) {
	
	        VisionManagement.getLogger().error( "Could not establish connection to vision: " + vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
		}

		mIncomingPacketManagement.resumeManagement();
		VisionManagement.getLogger().info( "Failed to set visionmode to " + aVisionMode );
		return false;
    	
    }
    
	@Override
	public PositionDataPackage getPositionData() {
		return mIncomingPacketManagement.getLatestPackage();
	}

}