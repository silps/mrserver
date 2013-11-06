package mrserver.core.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import mrserver.core.Core;

/**
 * Konfigurationsdaten des Servers
 * 
 * @author Eike Petersen
 *
 */
public class ServerConfig {
	
	// Server
	
	/**
	 * Der Name des Servers
	 * 
	 */
	private String mServerName = "";
	
	// Szenario
	
	/**
	 * Die Bibliothek des Szenarios und
	 * die Klasse des Szenarios
	 * 
	 */
	private String mScenarioLibrary = "";
	private String mScenarioClass = "";
	
	/**
	 * Die Konfigurationen des Szenarios
	 * 
	 */
	private String mScenarioConfigCmdLine = "";
	private String mScenarioConfigFile = "";
	
	// Netzwerk

	/**
	 * Die IP-Adresse und Port des Vision-Moduls
	 * 
	 */
	
	private InetAddress mVisionIPAddress = InetAddress.getLoopbackAddress();
	private int mVisionPort = -1;
	
	/**
	 * Die IP-Adresse und Port des BotControl-Moduls
	 * 
	 */
	
	private InetAddress mBotControlIPAddress = InetAddress.getLoopbackAddress();
	private int mBotControlPort = -1;
	
	/**
	 * Der Port fuer die Graphics-Module
	 * 
	 */
	
	private int mGraphicsPort = -1;
	
	/**
	 * Die Ports fuer die Bots
	 * 
	 */
	
	private List<Integer> mBotPorts;
	
	
	// Getter und Setters
	
	public String getServerName() {
        Core.getLogger().trace( "Getting servername " + mServerName );
		return mServerName;
	}

	public void setServerName(String aServerName) {
        Core.getLogger().debug( "Setting servername to " + aServerName );
		mServerName = aServerName;
	}

	public String getScenarioLibrary() {
        Core.getLogger().trace( "Getting scenariolibrary " + mScenarioLibrary );
		return mScenarioLibrary;
	}

	public void setScenarioLibrary(String aScenarioLibrary) {
        Core.getLogger().debug( "Setting scenariolibrary to " + aScenarioLibrary );
		mScenarioLibrary = aScenarioLibrary;
	}

	public String getScenarioClass() {
        Core.getLogger().trace( "Getting scenarioclass " + mScenarioClass );
		return mScenarioClass;
	}

	public void setScenarioClass(String aScenarioClass) {
        Core.getLogger().debug( "Setting scenarioclass to " + aScenarioClass );
		this.mScenarioClass = aScenarioClass;
	}

	public String getScenarioConfigCmdLine() {
        Core.getLogger().trace( "Getting scenarioconfigcmdline " + mScenarioConfigCmdLine );
		return mScenarioConfigCmdLine;
	}

	public void setScenarioConfigCmdLine(String aScenarioConfigCmdLine) {
        Core.getLogger().debug( "Setting scenarioconfigcmdline to " + aScenarioConfigCmdLine );
		this.mScenarioConfigCmdLine = aScenarioConfigCmdLine;
	}

	public String getScenarioConfigFile() {
        Core.getLogger().trace( "Getting scenarioconfigfile " + mScenarioConfigFile );
		return mScenarioConfigFile;
	}

	public void setScenarioConfigFile(String aScenarioConfigFile) {
        Core.getLogger().debug( "Setting scenarioconfigfile to " + aScenarioConfigFile );
		this.mScenarioConfigFile = aScenarioConfigFile;
	}

	public InetAddress getVisionIPAdress() {
        Core.getLogger().trace( "Getting visionipaddress " + mVisionIPAddress.getHostAddress() );
		return mVisionIPAddress;
	}

	public void setVisionIPAdress(InetAddress aVisionIPAddress) {
        Core.getLogger().debug( "Setting visionipaddress to " + aVisionIPAddress.getHostAddress() );
		this.mVisionIPAddress = aVisionIPAddress;
	}
	
	public void setVisionIPAdress( String aVisionIPAddress ) {
		
		Core.getLogger().debug( "Setting visionipaddress to " + aVisionIPAddress );
		try {
			
			setVisionIPAdress( InetAddress.getByName( aVisionIPAddress ) );
			
		} catch ( UnknownHostException vUnknownHostException ) {

            Core.getLogger().error( "Unkown Host: " + vUnknownHostException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vUnknownHostException );
            
		}

	}

	public int getVisionPort() {
        Core.getLogger().trace( "Getting visionports " + mVisionPort );
		return mVisionPort;
	}

	public void setVisionPort(int aVisionPort) {
        Core.getLogger().debug( "Setting visionports to " + aVisionPort );
		this.mVisionPort = aVisionPort;
	}

	public InetAddress getBotControlIPAdress() {
        Core.getLogger().trace( "Getting botcontrolipaddress " + mBotControlIPAddress.getHostAddress() );
		return mBotControlIPAddress;
	}

	public void setBotControlIPAdress( InetAddress aBotControlIPAddress ) {
        Core.getLogger().debug( "Setting botcontrolipaddress to " + aBotControlIPAddress.getHostAddress() );
		this.mBotControlIPAddress = aBotControlIPAddress;
	}

	public void setBotControlIPAdress( String aBotControlIPAddress ) {
		
		Core.getLogger().debug( "Setting botcontrolipaddress to " + aBotControlIPAddress );
		try {
			
			setBotControlIPAdress( InetAddress.getByName( aBotControlIPAddress ) );
			
		} catch ( UnknownHostException vUnknownHostException ) {

            Core.getLogger().error( "Unkown Botcontrolhost: " + vUnknownHostException.getLocalizedMessage() );
            Core.getLogger().catching( Level.ERROR, vUnknownHostException );
            
		}

	}

	public int getBotControlPort() {
        Core.getLogger().trace( "Getting botcontrolport " + mBotControlPort );
		return mBotControlPort;
	}

	public void setBotControlPort(int aBotControlPort) {
        Core.getLogger().debug( "Getting botcontrolport to " + aBotControlPort );
		this.mBotControlPort = aBotControlPort;
	}

	public int getGraphicsPort() {
        Core.getLogger().trace( "Getting graphicsport " + mGraphicsPort );
		return mGraphicsPort;
	}

	public void setGraphicsPort(int aGraphicsPort) {
        Core.getLogger().debug( "Setting graphicsport to " + aGraphicsPort );
		this.mGraphicsPort = aGraphicsPort;
	}

	public List<Integer> getBotPorts() {
		if( mBotPorts == null ){

	        Core.getLogger().debug( "Initialize botports " );
			mBotPorts = new ArrayList<Integer>();
			
		}
        Core.getLogger().trace( "Getting botports " + mBotPorts.toString() );
		return mBotPorts;
	}

	public void setBotPorts(List<Integer> aBotPorts) {
        Core.getLogger().debug( "Setting botports to " + aBotPorts );
		this.mBotPorts = aBotPorts;
	}
	
	public void addBotPort( int aBotPort ){
		
		if( mBotPorts == null ){

	        Core.getLogger().debug( "Initialize botports " );
			mBotPorts = new ArrayList<Integer>();
			
		}
        Core.getLogger().debug( "Adding botport " + aBotPort );
		mBotPorts.add( aBotPort );
		
	}

	@Override
	public String toString() {
		return "ServerConfig [mServerName=" + mServerName
				+ ", mScenarioLibrary=" + mScenarioLibrary
				+ ", mScenarioClass=" + mScenarioClass
				+ ", mScenarioConfigCmdLine=" + mScenarioConfigCmdLine
				+ ", mScenarioConfigFile=" + mScenarioConfigFile
				+ ", mVisionIPAddress=" + mVisionIPAddress + ", mVisionPort="
				+ mVisionPort + ", mBotControlIPAddress="
				+ mBotControlIPAddress + ", mBotControlPort=" + mBotControlPort
				+ ", mGraphicsPort=" + mGraphicsPort + ", mBotPorts="
				+ mBotPorts + "]";
	}
	
}
