/**
 * 
 * Character class created to create player location directions and life system
 *
 */
public class Character extends Sprite {
	//set variables
	private String name;
	private String character_img_up;
	private String character_img_down;
	private String character_img_left;
	private String character_img_right;
	private String character_icon;
	private int health = 3;
	private int score = 0;
	private int up = 0, down = 0, left = 0, right = 0;
	private int movement = 0;
	private boolean invulnerable = false;
	private int invulnerableTimer = 0;
	private boolean press = false;

	Character() {}
	
	Character(String name, String character_img_up, String character_img_down, String character_img_left,
			String character_img_right, String character_icon, String filename) {
		this.name = name;
		this.character_img_up = character_img_up;
		this.character_img_down = character_img_down;
		this.character_img_left = character_img_left;
		this.character_img_right = character_img_right;
		this.character_icon = character_icon;
		super.filename = filename;
	}
	//get character name
	public String getName() {
		return name;
	}
	//get character score
	public int getScore() {
		return score;
	}
	//increase character score
	public void increaseScore(int addScore) {
		this.score = addScore;
	}
	//minus character
	public void minusScore (){
		this.score -= 1;
	}
	//get character life
	public int getHp() {
		return health;
	}
	//get character life
	public int addHp() {
		this.health += 1;
		return health;
	}
	//set character life
	public void setHp(int hp) {
		this.health = hp;
	}
	//remove character life
	public void minusHp (){
		this.health --;
	}
	//set character name
	public void setName(String name) {
		this.name = name;
	}
	// check if button is pressed
	public boolean getPress() {
		return this.press;
	}

	public void setPress() {
		this.press = true;
	}

	public void setDepress() {
		this.press = false;
	}
	//get character movement
	public int getMovement() {
		return this.movement;
	}
	//set collide movement
	public void collideMovement(int num) {
		this.movement = this.movement-2;
	}
	//set increase movement
	public void incrementMovement() {
		this.movement += 1;
	}
	//set increase movement
	public void incrementMovement(int bonus) {
		this.movement += bonus;
	}
	//set decrease movement
	public void decrementMovement() {
		this.movement -= 1;
	}
	//get character move up
	public int getUp() {
		return this.up;
	}
	//get character move down
	public int getDown() {
		return this.down;
	}
	//get character move left
	public int getLeft() {
		return this.left;
	}
	//get character move right
	public int getRight() {
		return this.right;
	}
	//set character move up
	public void setUp(int up) {
		this.up = up;
	}
	//set character move down
	public void setDown(int down) {
		this.down = down;
	}
	//set character move left
	public void setLeft(int left) {
		this.left = left;
	}
	//set character move right
	public void setRight(int right) {
		this.right = right;
	}
	//get character icon
	public String getCharacter_icon() {
		return character_icon;
	}
	//set character icon
	public void setCharacter_icon(String character_icon) {
		this.character_icon = character_icon;
	}
	//get image of character moving up 
	public String getCharacter_img_up() {
		return character_img_up;
	}
	//set image of character moving up
	public void setCharacter_img_up(String character_img_up) {
		this.character_img_up = character_img_up;
	}
	//get image of character moving down
	public String getCharacter_img_down() {
		return character_img_down;
	}
	//set image of character moving down
	public void setCharacter_img_down(String character_img_down) {
		this.character_img_down = character_img_down;
	}
	//get image of character moving left
	public String getCharacter_img_left() {
		return character_img_left;
	}
	//set image of character moving left
	public void setCharacter_img_left(String character_img_left) {
		this.character_img_left = character_img_left;
	}
	//get image of character moving right
	public String getCharacter_img_right() {
		return character_img_right;
	}
	//set image of character moving right
	public void setCharacter_img_right(String character_img_right) {
		this.character_img_right = character_img_right;
	}
	//set character invisible timer
	public void setInvulnerable() {
		this.invulnerable = true;
		this.invulnerableTimer=150;
	}
	//unset character invisible timer
	public void unsetInvulnerable() {
		this.invulnerable = false;
		this.invulnerableTimer=0;
	}
	//get character invisible mode
	public boolean getInvulnerable() {
		return this.invulnerable;
	}
	//get character invisible timer
	public int getInvulnerableTimer() {
		return this.invulnerableTimer;
	}
	//count down invisible timer
	public void countDownInvulnerable() {
		this.invulnerableTimer--;
	}
}
