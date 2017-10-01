///////////////////////////////////////////////////////////////////////////////
// Main Class File:  CacheImageApp.java
// Files:            SingleImageReceiver.java
// Semester:         CS367 Fall 2016
//
// Author:           Jiayue Sheng
// Email:            jsheng7@wisc.edu
// CS Login:         jsheng
// Lecturer's Name:  Deb Deppeler
// Lecture Number:   001
//
///////////////////////////////////////////////////////////////////////////////
//
// Pair Partner:     Conrad Chen
// Email:            wchen283@wisc.edu
// CS Login:         conradc
// Lecturer's Name:  Deb Deppeler
// Lecture Number:	 002
/////////////////////////////////////////////////////////////////////////////


import java.io.IOException;
import java.util.Iterator;

/**
 * This class simulates a receiver application for a single image by maintaining
 * an image buffer, which is a linked list of packets of the transmitted image
 * file. It collects packets from our InputDriver and reconstructs the image
 * file using a PacketLinkedList&lt;SimplePacket&gt; for the image buffer.
 * 
 * @author honghui
 */
public class SingleImageReceiver {
	private InputDriver input;//the image file received
	private PacketLinkedList<SimplePacket> list;//the list containing packages

	/**
	 * Constructs a Receiver to obtain the image file transmitted.
	 * 
	 * @param file
	 *            the filename you want to receive
	 * 
	 * @throws IOException
	 *             if fails to retrieve the file
	 */
	public SingleImageReceiver(String file) throws IOException {
		//constructor of SingleImageReceiver
		input = new InputDriver(file);
		list = new PacketLinkedList<SimplePacket>();
	}

	/**
	 * Returns the PacketLinkedList buffer in the receiver
	 * 
	 * @return the PacketLinkedList object
	 */
	public PacketLinkedList<SimplePacket> getListBuffer() {
		//return the packages of the image
		return list;
	}

	/**
	 * Asks for retransmitting the packet with a sequence number. The requested
	 * packet will arrive later by using {@link #askForNextPacket()}. Notice
	 * that ONLY missing packet will be retransmitted. Pass seq=0 if the missing
	 * packet is the "End of Streaming Notification" packet.
	 * 
	 * @param seq
	 *            the sequence number of the requested missing packet
	 * @return true if the requested packet is added in the receiving queue;
	 *         otherwise, false
	 */
	public boolean askForMissingPacket(int seq) {
		//ask for missing packet
		return input.resendMissingPacket(seq);
	}

	/**
	 * Returns true if the maintained list buffer has a valid image content.
	 * Notice that when it returns false.
	 * 
	 * @return true if the maintained list buffer has a valid image content;
	 *         otherwise, false
	 */
	public boolean validImageContent() {
		return input.validFile(list);//check if the image content is valid
	}

	/**
	 * Returns the next packet.
	 * 
	 * @return the next SimplePacket object; returns null if no more packet to
	 *         receive
	 */
	public SimplePacket askForNextPacket() {
		//get the next packet from the file
		return input.getNextPacket();
	}

	/**
	 * Outputs the formatted content in the PacketLinkedList buffer. See course
	 * webpage for the formatting detail.
	 * 
	 * @param list
	 *            the PacketLinkedList buffer
	 */
	public void displayList(PacketLinkedList<SimplePacket> list) {
		// TODO
		// Create an iterator to iterate through the list
		PacketLinkedListIterator<SimplePacket> itr=list.iterator();
		
		// Print out the sequence number of each simplePacket object
		while(itr.hasNext()){
			System.out.print(itr.next().getSeq()+" ");
		}
		
		System.out.println();

	}

	/**
	 * Reconstructs the file by arranging the {@link PacketLinkedList} in
	 * correct order. It uses {@link #askForNextPacket()} to get packets until
	 * no more packet to receive. It eliminates the duplicate packets and asks
	 * for retransmitting when getting a packet with invalid checksum.
	 */
	public void reconstructFile() {
		// TODO
		// Receive packets in a new list without order and count the number 
		//of received packets.
		PacketLinkedList<SimplePacket> list = 
				new PacketLinkedList<SimplePacket>();
		//create a new list for use
		int sequenceNum = 0;
		while(true){
			SimplePacket temp  = askForNextPacket();
			//take packages from the file
			if(temp!=null){
				sequenceNum = temp.getSeq();
				if(sequenceNum>0){
					list.add(temp);	
					//add the package into list if its sequence is not EOF
				}
				else {
					//ignore the package of EOF
					break;
				}
			}
			else{
				break;
			}
		}
		//displayList(list);
		
		// Sort the list into the right order including duplicates packets
		PacketLinkedList<SimplePacket> newList=
				new PacketLinkedList<SimplePacket>();
		//create a new list for sorted packages
		Iterator<SimplePacket> spItr=list.iterator();
		//create a iterator
		while(spItr.hasNext()){//iterate through the list
			SimplePacket packet1=spItr.next();
			if(newList.size()==0){
				newList.add(packet1);
				//add the first package of list
			}else{
				Iterator<SimplePacket> spItr1=newList.iterator();
				int pos=0;
				while(spItr1.hasNext()){
					//check the received packages and place in order
					SimplePacket packet2=spItr1.next();
					if(packet1.getSeq()<packet2.getSeq()){
						//if the sequence number of packet1 is smaller than that
						// of packet2, add it into newList at position pos
						newList.add(pos,packet1);
						break;
					}else if(packet1.getSeq()>packet2.getSeq()){
						//if the sequence number of packet1 is larter than that 
						//of packet2, add it into newList at position pos+1
						pos++;
						if(!spItr1.hasNext()){
							newList.add(pos,packet1);
							break;
						}
					}else break;
					//do nothing when sequence number of packet1 equals to that
					//of packet2, therefore removed the duplicated packet
				}
			}
			
			
		}
	
		
		//Check if there is any lost packet and receive missing packets
		int i = 1;
		if(sequenceNum < 0){
			while(newList.size() < (-sequenceNum)){
				//while there is missing packet
				PacketLinkedListIterator<SimplePacket> itr1 = 
						newList.iterator();
				while(itr1.hasNext()){
					if(itr1.next().getSeq() != i){
						//if the sequence number of i th packet is not i
						if(askForMissingPacket(i)){
							newList.add(i-1, askForNextPacket());
							//add the missing packet
							
							
						}
						
					}
					i ++;
				}
			}
		}
		else if(sequenceNum > 0){
			//if the EOF packet is missing
			SimplePacket missingEnd;
			if(askForMissingPacket(0)){
				//ask the miaaing packet
				missingEnd = askForNextPacket();
				sequenceNum = missingEnd.getSeq();
				//update the size of the package by getting sequence number of 
				//EOF
			}
			while(newList.size() < (-sequenceNum)){
				PacketLinkedListIterator<SimplePacket> itr2 = 
						newList.iterator();
				while(itr2.hasNext()){
					if(itr2.next().getSeq() != i){
						//if the sequence number of i th packet is not i
						if(askForMissingPacket(i)){
							newList.add(i-1, askForNextPacket());
							//add the missing packet
							
							
						}
					}
					i ++;
				}
			}
		}
		
		this.list=newList;
		//set the list calling this method to the reference of newList
		
	}
}// End of SingleImageReceiver class
