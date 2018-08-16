/* This is the controller portion of game where responses to user events are handled.
   This is done using mouse events and action listeners. Once an event happens the model,
   or state of the game is changed accordingly */   

package controller;
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JLayeredPane;
import model.*;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.HashSet;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class Controller	extends JLayeredPane{
	private model.GameController game;
	private model.Actor currentActor;
	private model.CastingOffice castingOffice;
	private model.ExtraRoleInfo extraInfo;
   private DieRoll die;
   private int	rank;
	private boolean moved;
	private boolean clicked;
	
	public Controller(int x, int y,int h, int	w,
	model.GameController	game,	model.ExtraRoleInfo extraInfo) throws Exception{
		this.game =	game;
      this.castingOffice = new CastingOffice();
		this.extraInfo	= extraInfo;
      this.die =	new DieRoll(game);
		this.currentActor	= game.getCurrentActor();
		this.moved = false;
		this.clicked =	false;
		
      setBounds(x,y,h,w);
		setOpaque(false);
		add(die,new	Integer(2));
      
		initializeMoveButton();
		initializeActButton();
		initializeExtraRoleButton();
		initializeMainRoleButton();
		initializeRehearseButton();
		initializeUpgradeRankButton();
		initializeEndTurnButton();
				  
	 
 } 
  
	 public void initializeExtraRoleButton(){
			 JComboBox<String> roleOptions =	new JComboBox<String>();
			 roleOptions.setVisible(false);
			 roleOptions.setBounds(1227,380,150,20);
			 
			 roleOptions.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e){
			      currentActor = game.getCurrentActor();
					JComboBox roleOptions =	(JComboBox)e.getSource();
					String extraRole = (String)roleOptions.getSelectedItem();
					currentActor.becomeExtra();
					(((SceneRoom)game.findRoom(currentActor.getCurrentRoom()))).addExtra(currentActor,currentActor.getRank(),extraRole);
					roleOptions.setVisible(false);
					game.incrementTurn();
					moved	= false;
					clicked = false;
			   }
			});
			
         add(roleOptions,new Integer(0));		
			
			JButton takeExtraRoleButton =	new JButton("Take Extra Role");
			takeExtraRoleButton.setBackground(Color.orange);
			takeExtraRoleButton.setBounds(1227,350,200,30);
			takeExtraRoleButton.addMouseListener(new MouseAdapter() {
			  
			public void	mouseEntered(MouseEvent	e)	{
				currentActor =	game.getCurrentActor();
				if(currentActor.isUnemployed() && currentActor.isInSceneRoom()){
					model.SceneRoom sceneroom = (model.SceneRoom) game.findRoom(currentActor.getCurrentRoom());	 
					if(!sceneroom.shotsComplete()	&&	sceneroom.hasExtraRoleAvailable(currentActor.getRank())){		  
						takeExtraRoleButton.setBackground(Color.GREEN);
					}
				}
			 }
		  
			 public void mouseExited(MouseEvent	e){
				takeExtraRoleButton.setBackground(Color.orange);
			  }
			 
			 public void mouseClicked(MouseEvent e) {
					if(!clicked){
						clicked = true;
						clicked();
					}
			  }
	
			  private void	clicked(){
					System.out.println("clicked take extra role button");
					currentActor =	game.getCurrentActor();
					if(currentActor.isUnemployed() && currentActor.isInSceneRoom()){
						model.SceneRoom sceneroom = (model.SceneRoom) game.findRoom(currentActor.getCurrentRoom());
						if(!sceneroom.shotsComplete()	&&	sceneroom.hasExtraRoleAvailable(currentActor.getRank())){		  
							model.Room currentRoom = game.findRoom(currentActor.getCurrentRoom());
							Collection<String> extraRoles	= ((SceneRoom)currentRoom).getExtraRoles(currentActor.getRank());
							Vector<String>	extraRolesVector = new Vector<String>(extraRoles);
							DefaultComboBoxModel	model	= new	DefaultComboBoxModel(extraRolesVector);
							roleOptions.setModel(model);
							roleOptions.setVisible(true);
						}
					}
			};
		 });
		
      add(takeExtraRoleButton,new Integer(0));
	 
	 
	 }
	 
	 
	 public void initializeMainRoleButton(){
			 JComboBox<String> roleOptions =	new JComboBox<String>();
			 roleOptions.setVisible(false);
			 roleOptions.setBounds(1227,430,150,20);
			 add(roleOptions,new	Integer(0));
			 roleOptions.addActionListener(new ActionListener() {
			 
            public void	actionPerformed(ActionEvent e){
				   Actor	currentActor =	game.getCurrentActor();
					JComboBox roleOptions =	(JComboBox)e.getSource();
					String mainRole =	(String)roleOptions.getSelectedItem();
					currentActor.becomeMainActor();
					((SceneRoom)game.findRoom(currentActor.getCurrentRoom())).getScene().addActorAtRoleName(currentActor,mainRole);
					roleOptions.setVisible(false);
					game.incrementTurn();
					moved	= false;
					clicked = false;
				 }
			});
					
			JButton takeMainRoleButton	= new	JButton("Take Main Role");
			takeMainRoleButton.setBackground(Color.orange);
			takeMainRoleButton.setBounds(1227,400,200,30);
			takeMainRoleButton.addMouseListener(new MouseAdapter() {
			
			  public	void mouseEntered(MouseEvent e) {
					model.Actor	currentActor =	game.getCurrentActor();
					if(currentActor.isInSceneRoom() && currentActor.isUnemployed()){
						model.SceneRoom sceneroom = (model.SceneRoom) game.findRoom(currentActor.getCurrentRoom());
						if(!sceneroom.shotsComplete()	&&	sceneroom.getScene().hasMainRoleAvailable(currentActor.getRank())){
							takeMainRoleButton.setBackground(Color.GREEN);
						}
						
					}
			  }
		  
			  public	void mouseExited(MouseEvent e){
				takeMainRoleButton.setBackground(Color.orange);
			  }
			  
           public	void mouseClicked(MouseEvent e) {
					if(!clicked){
						clicked = true;
						clicked();
					}
			  }
	
			  private void	clicked(){
					System.out.println("clicked take main role");
					model.Actor	currentActor =	game.getCurrentActor();
					String currentRoomName = currentActor.getCurrentRoom();
					if((game.getCurrentActor().isUnemployed()	&&	game.getCurrentActor().isInSceneRoom())){
						model.SceneRoom sceneroom = (model.SceneRoom) game.findRoom(currentActor.getCurrentRoom());
						if(!sceneroom.shotsComplete()	&&	sceneroom.getScene().hasMainRoleAvailable(currentActor.getRank())){
							Collection<String> mainRoles = sceneroom.getMainRoleOptions(currentActor.getRank());
							Vector<String>	mainRolesVector =	new Vector<String>(mainRoles);
							DefaultComboBoxModel	model	= new	DefaultComboBoxModel(mainRolesVector);
							roleOptions.setModel(model);
							roleOptions.setVisible(true);
					  }
				 }
				}
		});
      add(takeMainRoleButton,new	Integer(0));
	
	 
	 
	 }
	 
	 
	 public void initializeMoveButton(){
			 JComboBox<String>	moveOptions	= new	JComboBox<String>();
			 moveOptions.setVisible(false);
			 moveOptions.setBounds(1227,230,150,20);
			 add(moveOptions,new	Integer(0));
			 moveOptions.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e){
   			   Actor	currentActor =	game.getCurrentActor();
   				JComboBox moveOptions =	(JComboBox)e.getSource();
   				String roomName =	(String)moveOptions.getSelectedItem();
   				model.Room newRoom =	game.findRoom(roomName);
   				game.findRoom(currentActor.getCurrentRoom()).removeActor(currentActor);
   				currentActor.changeRoom(roomName);
   				newRoom.addActor(currentActor);
   				moveOptions.setVisible(false);
			   }
			});
					
			JButton move =	new JButton("Move");
			move.setBackground(Color.orange);
			move.setBounds(1227,200,200,30);
			move.addMouseListener(new MouseAdapter() {
			  public	void mouseEntered(MouseEvent e) {
					if(game.getCurrentActor().isUnemployed() && !moved){
						move.setBackground(Color.GREEN);
					}
			  }
		  
			  public void mouseExited(MouseEvent	e){
			      move.setBackground(Color.orange);
			  }
			  
           public	void mouseClicked(MouseEvent e) {
					clicked();
			  }
	
			  private void	clicked(){
					System.out.println("clicked move");
					if(game.getCurrentActor().isUnemployed() && !moved){
						moved	= true;
						model.Actor	currentActor =	game.getCurrentActor();
						String currentRoomName = currentActor.getCurrentRoom();
						model.Room currentRoom = game.findRoom(currentRoomName);
						Collection<String> adjacentRooms	= currentRoom.getAdjacentRooms();
						Vector<String>	adjacentRoomVector =	new Vector<String>(adjacentRooms);
						DefaultComboBoxModel	model	= new	DefaultComboBoxModel(adjacentRoomVector);
						moveOptions.setModel(model);
						moveOptions.setVisible(true);
					}
			  }});
	        add(move,new Integer(0));
	 
	 }
	 
	 public void initializeActButton(){
   		JButton act	= new	JButton("Act");
   		act.setBackground(Color.orange);
   		act.setBounds(1227,150,200,30);
   		add(act,new	Integer(0));
   		act.addMouseListener(new MouseAdapter() {
   		  
           public	void mouseEntered(MouseEvent e) {
   				if(!game.getCurrentActor().isUnemployed()){
   					act.setBackground(Color.GREEN);
   				}
   		  }
   		  
   		  public	void mouseExited(MouseEvent e){
   		    act.setBackground(Color.orange);
   		  }
   		  
           public	void mouseClicked(MouseEvent e) {
   				clicked();
   		  }
   		  
           private void	clicked(){
      			System.out.println("clicked act");
      			if(!game.getCurrentActor().isUnemployed()	&&	!die.rollingForBonus()){
      					die.activateRollButton();
      					if(!die.rollingForBonus()){
      						die.resetRoll();
      					}			
      			}
   		  }
   		 
         }); 
	 }
	 
	 private	void initializeRehearseButton(){
   		JButton rehearse = new JButton("Rehearse");
   		rehearse.setBackground(Color.orange);
   		rehearse.setBounds(1227,250,200,30);
   		rehearse.addMouseListener(new	MouseAdapter()	{
   		  
           public	void mouseEntered(MouseEvent e) {
   			 if(!game.getCurrentActor().isUnemployed()){
   					rehearse.setBackground(Color.GREEN);
   				}
   		  }
   		  
   		  public	void mouseExited(MouseEvent e){
   			 rehearse.setBackground(Color.orange);
   		  }
   		  
           public	void mouseClicked(MouseEvent e) {
   				clicked();
   		  }
   		  
           private void clicked(){
   				if(!game.getCurrentActor().isUnemployed()){
   					game.getCurrentActor().rehearse();	
   					System.out.println("clicked");
   					game.incrementTurn();
   				}
   		  }
   		  
   		});
         add(rehearse,new Integer(0));

	 
	 }
	 
	 private	void initializeUpgradeRankButton(){
		
      JComboBox<String>	upg =	new JComboBox<String>();
		upg.setVisible(false);
		upg.setBounds(1227,330,150,20);
		upg.addActionListener(new ActionListener() {
   		public void	actionPerformed(ActionEvent e){
   				  Actor currentActor	= game.getCurrentActor();
   				  if(currentActor.getCurrentRoom().equals("Casting Office")){
   						JComboBox upg = (JComboBox)e.getSource();
   						String upgradeOption	= (String)upg.getSelectedItem();
   						Scanner sc = new Scanner(upgradeOption);
   						int upgradeRank =	sc.useDelimiter("\\D+").nextInt();
   						int cost	= sc.useDelimiter("\\D+").nextInt();
   						System.out.println(upgradeRank);
   						if(upgradeOption.contains("dollars")){
   							currentActor.updateWallet(-1 * cost,0);
   						} else {
   							currentActor.updateWallet(0,-1 *	cost);
   						}
   						currentActor.upgradeRank(upgradeRank);
   				  }
   						upg.setVisible(false);
   	  }
	  });
     
      add(upg,new	Integer(0));

		JButton rankUp	= new	JButton("Upgrade Rank");
		rankUp.setBackground(Color.orange);
		rankUp.setBounds(1227,300,200,30);
		rankUp.addMouseListener(new MouseAdapter() {
			 public void mouseEntered(MouseEvent e) {
				model.Actor	currentActor =	game.getCurrentActor();
				if(currentActor.getCurrentRoom().equals("Casting Office")){
					if(castingOffice.canAffordUpgrade(currentActor.getCredit(),currentActor.getMoney(),currentActor.getRank())){
						rankUp.setBackground(Color.GREEN);
					}
				}
			 }	
		  
		  public	void mouseExited(MouseEvent e){
			 rankUp.setBackground(Color.orange);
		  }

		  public void mouseClicked(MouseEvent e) {
				clicked();
		  }
			 
        private void clicked(){
				  if(currentActor.getCurrentRoom().equals("Casting Office")){
						System.out.println("clicked upgrade rank");
						model.Actor	currentActor =	game.getCurrentActor();
						String currentRoomName = currentActor.getCurrentRoom();
						model.Room currentRoom = game.findRoom(currentRoomName);
						Collection<String> buyRanks =	castingOffice.getUpgradeOptions(currentActor.getCredit(),currentActor.getMoney(),currentActor.getRank());
						Vector<String>	buyRanksVector	= new	Vector<String>(buyRanks);
						DefaultComboBoxModel	model	= new	DefaultComboBoxModel(buyRanksVector);
						upg.setModel(model);
						upg.setVisible(true);
				  }
			  }

			 });
       add(rankUp,new	Integer(0));
	 
	 }
	 
	 public void initializeEndTurnButton(){
		
      JButton endTurn =	new JButton("End Turn");
		endTurn.setBackground(Color.orange);
		endTurn.setBounds(1227,450,200,30);
		endTurn.addMouseListener(new MouseAdapter() {
		  
        public	void mouseEntered(MouseEvent e) {
			 if(game.getCurrentActor().isUnemployed()){
					endTurn.setBackground(Color.GREEN);
				}
		  }
		  
		  public	void mouseExited(MouseEvent e){
			 endTurn.setBackground(Color.orange);
		  }
		  
        public	void mouseClicked(MouseEvent e) {
				clicked();
		  }
			 
        private	void clicked(){
		      if(game.getCurrentActor().isUnemployed()){
				      moved	= false;
				      game.incrementTurn();
				      System.out.println("clicked");
				}
		  }
		  
		});
        
      add(endTurn,new Integer(0));
	 }
	 
	 
	 
	 
	 
	
	 

 }
 
 
	
		  
		