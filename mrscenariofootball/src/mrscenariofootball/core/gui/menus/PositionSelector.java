package mrscenariofootball.core.gui.menus;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;

import javax.swing.DefaultComboBoxModel;

import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.gui.PlayField;
import mrscenariofootball.game.Core;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class PositionSelector extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox<ReferencePointName> comboBox;
	
	private ReferencePointName mSelectedPoint;
	private Component mInvoker;

	public PositionSelector( Component aInvoker ) {
		setTitle("Select position to place ball");
		setResizable(false);
		mInvoker = aInvoker;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 321, 96);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			comboBox = new JComboBox<ReferencePointName>();
			comboBox.setPreferredSize(new Dimension(300, 22));
			comboBox.setMinimumSize(new Dimension(300, 22));
			DefaultComboBoxModel<ReferencePointName> vModel = new DefaultComboBoxModel<ReferencePointName>( ReferencePointName.values() );
			vModel.removeElement( ReferencePointName.Ball );
			vModel.removeElement( ReferencePointName.NoFixedName );
			vModel.removeElement( ReferencePointName.Player );
			contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			comboBox.setModel( vModel );
			contentPanel.add(comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						
						mSelectedPoint = (ReferencePointName) comboBox.getSelectedItem();

						BallPosition vNewPosition = new BallPosition( ReferencePointName.Ball,
		    					new ServerPoint( mSelectedPoint.getRelativePosition().getX() * ScenarioInformation.getInstance().getXFactor(),
		    								     mSelectedPoint.getRelativePosition().getY() * ScenarioInformation.getInstance().getYFactor()));
		            	
		            	ScenarioInformation.getInstance().setBall( vNewPosition );
		            	Core.getInstance().stopBall();
		            	ScenarioCore.getLogger().info( "Ball set to position: {}", vNewPosition );
		            	((PlayField) mInvoker).update();
		            	
		            	dispose();
		            	
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						dispose();
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public ReferencePointName getSelectedPoint() {
		return mSelectedPoint;
	}

}
