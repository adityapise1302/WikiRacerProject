/*
 * AUTHOR: Aditya Pise
 * FILE: MaxPQ.java
 * ASSIGNMENT: Programming Assignment 10 - WikiRacer
 * COURSE: CSC 210; Spring 2022
 * PURPOSE: The purpose of this code is to create a priority queue backed by
 * binary max heap. It has methods growArray(), bubbleUp(), enqueue(), 
 * bubbleDown(), dequeue(), isEmpty(), and Ladder class. The growArray() 
 * increases the array, bubbleUp() bubbles up the value to the proper
 * position, dequeue() removes the highest priority object from the priority
 * queue, bubbleDown() bubbles down the value to the proper position, and 
 * isEmpty() checks whether the queue is empty or not. Ladder class is 
 * a private class which stores the ladder list and the priority.
 */
import java.util.List;

public class MaxPQ {
	private static final int INITIAL_SIZE = 10;
	private Ladder[] ladders;
	private int size;
	
	/*
     * This is the constructor of the MaxPQ class. It initializes the
     * array to a size of 10. It also sets the size to zero.
     */
	public MaxPQ() {
		this.ladders = new Ladder[INITIAL_SIZE];
		this.size = 0;
	}
	
	/*
     * This is a private helper method which helps in increasing the size of the
     * array when the number of ladders in the queue equal to the size of the
     * array and there is no more space for more ladders.
     */
	private void growArray() {
		Ladder[] tempArr = new Ladder[this.ladders.length * 2];
        for (int i = 1; i <= this.size; i++) {
            tempArr[i] = this.ladders[i];
        }
        this.ladders = tempArr;
	}
	
	/*
     * This is private helper method which helps in bubbling up the ladders from
     * the provided index to its proper location.
     * 
     * @param - index is the index of the ladder in the array which is to be
     * bubbled up. It is an integer.
     */
	private void bubbleUp(int index) {
		if (index > 1) {
            int parentIndex = index / 2;
            Ladder atParentIndex = this.ladders[parentIndex];
            Ladder atIndex = this.ladders[index];
            if (atParentIndex.priority < atIndex.priority) {
                this.ladders[parentIndex] = atIndex;
                this.ladders[index] = atParentIndex;
                bubbleUp(parentIndex);
            }
        }
	}
	
	/*
     * This method adds the ladder to the queue according to the provided
     * priority.
     * 
     * @param - links, is the List of strings suggesting the ladder. It is a 
     * 			List.
     * 			priority, is the priority of the ladder. It is an integer.
     */
	public void enqueue(List<String> links, int priority) {
		if(this.ladders.length == this.size + 1) {
			growArray();
		}
		Ladder providedLadder = new Ladder(links, priority);
		this.ladders[this.size + 1] = providedLadder;
		this.size++;
		if(this.size > 1) {
			bubbleUp(this.size);
		}
	}
	
	/*
     * This is a private helper method to bubble down the provided ladder at
     * the index in the queue according to the priority.
     * 
     * @param - index is the index of the ladder in the array to be bubbled
     * down, it is an integer.
     */
	private void bubbleDown(int index) {
		if(index < this.size) {
            Ladder leastPriority = this.ladders[index];
            if ((index * 2) <= this.size) {
            	Ladder leftChild = this.ladders[index * 2];
                if (leftChild.priority > leastPriority.priority) {
                    leastPriority = leftChild;
                }
            }
            if ((index * 2 + 1) <= this.size) {
            	Ladder rightChild = this.ladders[index * 2 + 1];
                if (rightChild.priority > leastPriority.priority) {
                    leastPriority = rightChild;
                }
            }
            if (leastPriority != this.ladders[index]) {
                Ladder temp = this.ladders[index];
                if (leastPriority == this.ladders[index * 2]) {
                    this.ladders[index] = this.ladders[index * 2];
                    this.ladders[index * 2] = temp;
                    bubbleDown(index * 2);
                } else {
                    this.ladders[index] = this.ladders[index * 2 + 1];
                    this.ladders[index * 2 + 1] = temp;
                    bubbleDown(index * 2 + 1);
                }
            }
		}
	}
	
	/*
     * This method removes the ladder in front of the queue from the queue. If
     * the queue is empty then it returns null.
     * 
     * @return - a List suggesting the ladder of Wikipedia pages.
     */
	public List<String> dequeue() {
		if(!this.isEmpty()) {
			Ladder atFirst = this.ladders[1];
	        this.ladders[1] = this.ladders[this.size];
	        this.size--;
	        if (this.size > 0) {
	            bubbleDown(1);
	        }
			return atFirst.links;
		}
		return null;
	}
	
	/*
     * This method suggest whether the queue is empty or not.
     * 
     * @return - a boolean value suggesting whether the queue is empty or not.
     */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/*
	 * This is a private class storing the list containing the ladder of the 
	 * Wikipedia pages as elements and the priority.
	 */
	private class Ladder{
		private List<String> links;
		private int priority;
		
		/*
		 * This is the constructor of the Ladder class which initializes the links 
		 * List to the provided list and the priority to the provided priority.
		 * 
		 * @param - links, is the List of string suggesting the ladder of Wikipedia 
		 * 			pages.
		 * 		    priority, is the integer suggesting the priority of the ladder.
		 */
		public Ladder(List<String> links, int priority) {
			this.links = links;
			this.priority = priority;
		}
	}
}
