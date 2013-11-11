package mrserver.core.vision.network;

import java.io.IOException;

import org.apache.logging.log4j.Level;

import mrserver.core.Core;
import mrserver.core.vision.VisionManagement;
import mrservermisc.network.BasicUDPServerConnection;
import mrservermisc.network.data.position.VisionMode;
import mrservermisc.network.handshake.ConnectionAcknowlege;
import mrservermisc.network.handshake.ConnectionEstablished;
import mrservermisc.network.handshake.ConnectionRequest;

public class VisionConnection extends BasicUDPServerConnection{
	
	private boolean mIsConnectionEstablished = false;
	
	public VisionConnection() throws IOException{
		
		super( Core.getInstance().getServerConfig().getVisionIPAdress(), Core.getInstance().getServerConfig().getVisionPort() );
				
	}
	
	public boolean establishConnection(){
		
		try {
		
			VisionManagement.getLogger().info( "Establishing connection: " + toString() );
			
			ConnectionRequest vRequestToVision = new ConnectionRequest( Core.getInstance().getServerConfig().getServerName() );
			VisionManagement.getLogger().debug( "Sending handshake: " + vRequestToVision.toString() );
			sendDatagrammString( vRequestToVision.toXMLString() );
			
			ConnectionAcknowlege vVisionAcknowledge = ConnectionAcknowlege.unmarshallXMLConnectionAcknowlegeString( getDatagrammString( 1000 ) );
			
			VisionManagement.getLogger().debug( "Recieving Acknowledge: " + vVisionAcknowledge );
			
			if( vVisionAcknowledge != null && vVisionAcknowledge.isConnectionAllowed() ){
				
				ConnectionEstablished vVisionConnectionEstablished = new ConnectionEstablished();
				VisionManagement.getLogger().debug( "Sending Acknowledge: " + vVisionConnectionEstablished );
				sendDatagrammString( vVisionConnectionEstablished.toXMLString() );
				
				mIsConnectionEstablished = true;
				return mIsConnectionEstablished;
				
			}
	
		} catch ( Exception vException ) {
	
	        VisionManagement.getLogger().error( "Could not establish connection to vision: " + vException.getLocalizedMessage() );
	        VisionManagement.getLogger().catching( Level.ERROR, vException );
	        
		}

		VisionManagement.getLogger().debug( "Could not establish connection" );
		return false;
		
	}
	
	@Override
	public boolean isConnected() {
		
		return super.isConnected() && mIsConnectionEstablished;
		
	}

}