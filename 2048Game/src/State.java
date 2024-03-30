package edu.wm.cs.cs301.game2048;

import java.util.Arrays;

public class State implements GameState {
	
	public int[][] gameboard = new int [4][4];
	
	//2nd constructor for setting the board to equal the Gamestate object: original 
	public State(GameState original) {
		for (int x=0; x<4; x++){
			for (int y=0;y<4; y++){
				this.setValue(x, y, original.getValue(x, y));
			}
		}
	}
	//constructor initializing the empty game board 
	public State() {
		setEmptyBoard();
		
	}

	//returns the [x][y] value in the 2D Array
	@Override
	public int getValue(int xCoordinate, int yCoordinate) {
		return gameboard[xCoordinate][yCoordinate];
	}

	//sets  the [x][y] value in the 2D Array
	@Override
	public void setValue(int xCoordinate, int yCoordinate, int value) {
		this.gameboard[xCoordinate][yCoordinate] = value;
				
	}

	//Sets every tile on the board to equal 0
	@Override
	public void setEmptyBoard() {
		for (int x=0; x<4; x++){
			for (int y=0;y<4; y++){
				this.setValue(x, y, 0);
			}
		}

	}

	//if the board has space, a tile of value 2 or 4 is randomly selected and randomly generated
	@Override
	public boolean addTile() {
		if (this.isFull()){
			return false;
		}
		while (true){
			int xrand = (int)(Math.random()*4.0);
			int yrand = (int)(Math.random()*4.0);
			int tile = (int)((Math.random()*2.0)+1)*2;
			if (this.getValue(xrand, yrand) == 0){
				gameboard[xrand][yrand] = tile;
				return true;
			}
		}
	
	
	}

	//checks if every value on the game board is greater than 0
	@Override
	public boolean isFull() {
		for (int x=0; x<4; x++){
			for (int y=0;y<4; y++){
				if (this.getValue(x,y)==0){
					return false;
				}
			}
		}
		return true;
	}

	//checks every tiles vertical and horizontal neighbors for equal values
	@Override
	public boolean canMerge() {
		//checking each tile's right most neighbors for equality 
		for (int y=0; y<4; y++){
			for (int x=0; x<3; x++){
				if (this.getValue(x, y)== (this.getValue(x+1, y))){
					return true;
				}
			}
		}
		
		//checking each tile's lower most neighbor for equality
		for (int x=0; x<4; x++){
			for (int y=0; y<3; y++){
				if (this.getValue(x, y)== (this.getValue(x, y+1))){
					return true;
				}
			}
		}
		return false;
	}

	//checks if the 2048 tile exists on the board
	@Override
	public boolean reachedThreshold() {
		for (int x=0; x<4; x++){
			for (int y=0; y<4; y++){
				if (this.getValue(x, y) == 2048) {
					return true;
				}
			}
		}
		return false;
	}

	//merges all tiles with pairs to their left and slides them left
	//this operation is done 3 times to fully slide the tiles
	@Override
	public int left() {
		int sum =0;
		for (int y=0; y<4; y++){
			int matches = 0;
			for(int count=0; count<3; count++) {
				for (int x=1; x<4; x++){
					if (this.getValue(x-1, y) == 0) {
						this.setValue(x-1, y, this.getValue(x, y));
						this.setValue(x, y, 0);
					}
					else if (this.getValue(x-1, y) == this.getValue(x, y) && (x>matches) ){
						int val = this.getValue(x-1, y);
						sum += val *2;
						this.setValue(x-1, y, val*2);
						this.setValue(x, y, 0);
						matches+= x; 
					}
				}
			}
		}
		return sum;
	}
	
	
	@Override
	public int right(){
		int sum =0;
		for (int y=0; y<4; y++){
			int matches = 0;
			for(int count=0; count<3; count++) {
				for (int x=2; x>=0; x--){
					if (this.getValue(x+1, y) == 0) {
						this.setValue(x+1, y, this.getValue(x, y));
						this.setValue(x, y, 0);
					}
					else if (this.getValue(x+1, y) == this.getValue(x, y) && (-1*(x-3)>matches)){
						int val = this.getValue(x+1, y);
						sum += val *2;
						this.setValue(x+1, y, val*2);
						this.setValue(x, y, 0);
						matches+=-1*(x-3);
					}
				}	
			}
		}
		return sum;
	}

	//merges all tiles with pairs below them and slides them down
	@Override
	public int down() {
		int sum =0;
		for (int x=0; x<4; x++){
			int matches = 0;
			for(int count=0; count<3; count++) {
				for (int y=2; y>=0; y--){
					if (this.getValue(x, y+1) == 0) {
						this.setValue(x, y+1, this.getValue(x, y));
						this.setValue(x, y, 0);
					}
					else if (this.getValue(x, y+1) == this.getValue(x, y) && (-1*(y-3)>matches)){
						int val = this.getValue(x, y+1);
						sum += val *2;
						this.setValue(x, y+1, val*2);
						this.setValue(x, y, 0);
						matches+= -1*(y-3);
					}
				}
			}
		}	
		return sum;
	}

	//merges all tiles with pairs above them and slides them up
	@Override
	public int up() {
		int sum =0;
		for (int x=0; x<4; x++){
			int matches = 0;
			for(int count=0; count<3; count++) {
				for (int y=1; y<4; y++){
					if (this.getValue(x, y-1) == 0) {
						this.setValue(x, y-1, this.getValue(x, y));
						this.setValue(x, y, 0);
					}
					else if (this.getValue(x, y-1) == this.getValue(x, y) && (y>matches)){
						int val = this.getValue(x, y-1);
						sum += val *2;
						this.setValue(x, y-1, val*2);
						this.setValue(x, y, 0);
						matches+=y;
					}
				}
			}
		}	
		return sum;
	}

	//hashes the object instance 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(gameboard);
		return result;
	}

	//checks equality between the current board and the one being referenced
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		return Arrays.deepEquals(gameboard, other.gameboard);
	}
	
	
}


