/* UserList.java */

import queue.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;

public class UserList {

    private CatenableQueue<User> userQueue;
    private int size;

    /**
    * Creates empty UserList containing no users
    **/
    public UserList(){
        userQueue = new CatenableQueue<User>();
        size = 0;
    }

    /**
    *  addUser() adds a defensive copy of the specified user.
    **/
    public void add(User u){
        if (u.getPagesPrinted() < 0) {
            System.out.println("A user cannot have a negative number of pages printed.");
            return;
        }
        User uCopy = new User(u.getId(), u.getPagesPrinted());
        userQueue.enqueue(uCopy);
        size++;
    }

    /**
    *  getSize() returns the number of users in the UserList
    **/
    public int getSize(){
        return size;
    }

    /**
    * getUsers() returns the CatenableQueue<User> of Users.
    **/
    public CatenableQueue<User> getUsers(){
        return userQueue;
    }

    /**
    *  toString() prints out the id and pages printed of all users in the UserList.
    **/
    public String toString(){
        return userQueue.toString();
    }

    /**
    *  partition() partitions qUnsorted using the pivot integer.  On completion of
    *  this method, qUnsorted is empty, and its items have been moved to qLess,
    *  qEqual, and qGreater, according to their relationship to the pivot.
    *
    *   @param sortFeature is a string that tells us what we are sorting. If we are
    *       sorting user IDs, sortFeatures equals "id". If we are sorting pages
    *       printed, sortFeatures equals "pages".
    *   @param qUnsorted is a CatenableQueue<User> of User objects.
    *   @param pivot is an integer used for partitioning.
    *   @param qLess is a CatenableQueue<User>, in which all Users with sortFeature less than pivot
    *       will be enqueued.
    *   @param qEqual is a CatenableQueue<User>, in which all Users with sortFeature equal to the pivot
    *       will be enqueued.
    *   @param qGreater is a CatenableQueue<User>, in which all Users with sortFeature greater than pivot
    *       will be enqueued.  
    **/ 
    public static void partition(String sortFeature, CatenableQueue<User> qUnsorted, int pivot, 
        CatenableQueue<User> qLess, CatenableQueue<User> qEqual, CatenableQueue<User> qGreater){
        while (!qUnsorted.isEmpty()) {
            User item = qUnsorted.dequeue();
            int val;
            if (sortFeature == "id") {
                val = item.getId();
            } else {
               val = item.getPagesPrinted();
            }

            if (val > pivot) {
                qGreater.enqueue(item);
            } else if (val < pivot) {
                qLess.enqueue(item);
            } else {
                qEqual.enqueue(item);
            }
        }
    }

    /**
    *   quickSort() sorts q from smallest to largest according to sortFeature using quicksort.
    *   @param sortFeature is a string that tells us what we are sorting. If we are
    *       sorting user IDs, sortFeatures equals "id". If we are sorting pages
    *       printed, sortFeatures equals "pages".
    *   @param q is an unsorted CatenableQueue containing User items.
    **/
    public static void quickSort(String sortFeature, CatenableQueue<User> q){ 
        if (q.isEmpty()) {
        	return;
        }
        CatenableQueue<User> less = new CatenableQueue<User>();
        CatenableQueue<User> greater = new CatenableQueue<User>();
        CatenableQueue<User> equal = new CatenableQueue<User>();
        Random rand = new Random();
        int pivot;
        int random = rand.nextInt(q.size());
        User item = q.nth(random);
        if (sortFeature == "id") {
                pivot = item.getId();
        } else {
                pivot = item.getPagesPrinted();
        }

        partition(sortFeature, q, pivot, less, equal, greater);
        if (!less.isEmpty()) {
        quickSort(sortFeature, less);
        } if (!greater.isEmpty()) {
        quickSort(sortFeature, greater);
        }
        q.append(less);
        q.append(equal);
        q.append(greater);
    }

    /**
    *  quickSort() sorts userQueue from smallest to largest according to sortFeature, using quicksort.
    *  @param sortFeature is a string that equals "id" if we are sorting users by their IDs, or equals
    *  "pages" if we are sorting users by the number of pages they have printed.
    **/
    public void quickSort(String sortFeature){
        quickSort(sortFeature, userQueue);
    }


    /**
    *  makeQueueOfQueues() makes a queue of queues, each containing one User
    *  of userQueue.  Upon completion of this method, userQueue is empty.
    *  @return a CatenableQueue<CatenableQueue<User>>, where each CatenableQueue
    *    contains one User from userQueue.
    **/
    public CatenableQueue<CatenableQueue<User>> makeQueueOfQueues(){
        CatenableQueue<CatenableQueue<User>> queueOfQueues = new CatenableQueue<CatenableQueue<User>>();
        while (!userQueue.isEmpty()) {
            User item = userQueue.dequeue();
            CatenableQueue<User> temp = new CatenableQueue<User>();
            temp.enqueue(item);
            queueOfQueues.enqueue(temp);
        }
        return queueOfQueues;
    }

    /**
    *  mergeTwoQueues() merges two sorted queues into one sorted queue.  On completion
    *  of this method, q1 and q2 are empty, and their Users have been merged
    *  into the returned queue. Assume q1 and q2 contain only User objects.
    *   @param sortFeature is a string that tells us what we are sorting. If we are
    *       sorting user IDs, sortFeatures equals "id". If we are sorting pages
    *       printed, sortFeatures equals "pages".
    *  @param q1 is CatenableQueue<User> of User objects, sorted from smallest to largest by their sortFeature.
    *  @param q2 is CatenableQueue<User> of User objects, sorted from smallest to largest by their sortFeature.
    *  @return a CatenableQueue<User> containing all the Users from q1 and q2 (and nothing else),
    *       sorted from smallest to largest by their sortFeature.
    **/
    public static CatenableQueue<User> mergeTwoQueues(String sortFeature, CatenableQueue<User> q1, CatenableQueue<User> q2){
        CatenableQueue<User> sorted = new CatenableQueue<User>();
        if (q1 == null) {
        	return q2;
        }
        if (q2 == null) {
        	return q1;
        }
        while (!q1.isEmpty() && !q2.isEmpty()) {
            User item1 = q1.front();
            User item2 = q2.front();
            int val1, val2;
            if (sortFeature == "id") {
                val1 = item1.getId();
                val2 = item2.getId();
            } else {
               val1 = item1.getPagesPrinted();
               val2 = item2.getPagesPrinted();
            }

            if (val2 < val1) {
                sorted.enqueue(item2);
                q2.dequeue();
            } else {
                sorted.enqueue(item1);
                q1.dequeue();
            }
        }

        while (!q1.isEmpty()) {
            User item1 = q1.dequeue();
            int val1;
            if (sortFeature == "id") {
                val1 = item1.getId();
            } else {
               val1 = item1.getPagesPrinted();
            }
            sorted.enqueue(item1);
        }

        while (!q2.isEmpty()) {
            User item2 = q2.dequeue();
            int val2;
            if (sortFeature == "id") {
                val2 = item2.getId();
            } else {
               val2 = item2.getPagesPrinted();
            }
            sorted.enqueue(item2);
        }
        return sorted;
    }

    /**
    *   mergeSort() sorts this UserList from smallest to largest according to sortFeature using mergesort.
    *   You should complete this method without writing any helper methods.
    *   @param sortFeature is a string that tells us what we are sorting. If we are
    *       sorting user IDs, sortFeatures equals "id". If we are sorting pages
    *       printed, sortFeatures equals "pages".
    **/
    public void mergeSort(String sortFeature) {
    	CatenableQueue<CatenableQueue<User>> queues = makeQueueOfQueues();
    	if (queues.isEmpty()) {
    		userQueue = new CatenableQueue<User>();
    		return;
    	}

    	while (queues.size() > 1) {
    		queues.enqueue(mergeTwoQueues(sortFeature, queues.dequeue(), queues.dequeue()));
    	}

    	userQueue = queues.dequeue();
    }


    //    public void mergeSortRecursive(String sortFeature) {
    //     int prevSize = this.size;
    //     int mid = this.size/2;

    //     UserList firstHalf = new UserList();
    //     UserList secondHalf = new UserList();

    //     if (prevSize == 1) {
    //     	return;
    //     }

    //     for (int i = 0; i < mid; i++){
    //         firstHalf.add(userQueue.dequeue());
    //     }

    //     for (int i = mid; i < this.size; i++){
    //         secondHalf.add(userQueue.dequeue());
    //     }

    //     if (prevSize == 2) {
    //     	userQueue = mergeTwoQueues(sortFeature, (firstHalf.userQueue), (secondHalf.userQueue));
    //     	return;
    //     }
        
    //     firstHalf.mergeSort(sortFeature);
    //     secondHalf.mergeSort(sortFeature);
    //     userQueue = mergeTwoQueues(sortFeature, (firstHalf.userQueue), (secondHalf.userQueue));
    // }

    /**
    *   sortByBothFeatures() sorts this UserList's userQueue from smallest to largest pages printed.
    *   If two Users have printed the same number of pages, the User with the smaller user ID is first.
    **/
    public void sortByBothFeatures(){
        quickSort("id");
        quickSort("pages");
    }


    @Test
    public void naivePartitionTest() {
        UserList list = new UserList();

        list.add(new User(0, 20));
        list.add(new User(1, 0));
        list.add(new User(2, 10));

        CatenableQueue<User> less = new CatenableQueue<User>();
        CatenableQueue<User> equal = new CatenableQueue<User>();
        CatenableQueue<User> greater = new CatenableQueue<User>();

        /* pivot on user 1 by id */
        list.partition("id", list.userQueue, 1, less, equal, greater);
        assertEquals(1, less.size());
        assertEquals(1, equal.size());
        assertEquals(1, greater.size());
        assertEquals(new User(0, 20), less.front());
        assertEquals(new User(1, 0), equal.front());
        assertEquals(new User(2, 10), greater.front());
    }

    @Test
    public void naiveQuickSortTest() {
        UserList list = new UserList();
        list.add(new User(2, 12));
        list.add(new User(0, 10));
        list.add(new User(1, 11));

        list.quickSort("id");

        String sorted =
         "[ User ID: 0, Pages Printed: 10,\n  User ID: 1, Pages Printed: 11,\n  User ID: 2, Pages Printed: 12 ]";

        assertEquals(sorted, list.toString());

        list.quickSort("pages");
        assertEquals(sorted, list.toString()); 
    }

    @Test
    public void naiveMakeQueuesTest(){
        UserList list = new UserList();

        list.add(new User(0, 20));
        list.add(new User(1, 0));
        list.add(new User(2, 10));

        CatenableQueue<CatenableQueue<User>> queues = list.makeQueueOfQueues();
        String queueOfQueues = 
        "[ [ User ID: 0, Pages Printed: 20 ],\n  [ User ID: 1, Pages Printed: 0 ],\n  [ User ID: 2, Pages Printed: 10 ] ]";

        assertEquals(queueOfQueues, queues.toString());        
    }

    @Test
    public void naiveMergeQueuesTest(){
        CatenableQueue<User> q1 = new CatenableQueue<User>();
        CatenableQueue<User> q2 = new CatenableQueue<User>();
        q1.enqueue(new User(0, 20));
        q1.enqueue(new User(2, 30));
        q1.enqueue(new User(3, 40));
        q2.enqueue(new User(1, 0));
        q2.enqueue(new User(4, 10));

        CatenableQueue<User> merged = mergeTwoQueues("pages", q1, q2);
        String mergeByPages = 
        "[ User ID: 1, Pages Printed: 0,\n  User ID: 4, Pages Printed: 10,\n  User ID: 0, Pages Printed: 20,\n  User ID: 2, Pages Printed: 30,\n  User ID: 3, Pages Printed: 40 ]";
        assertEquals(mergeByPages, merged.toString());        

        q1 = new CatenableQueue<User>();
        q2 = new CatenableQueue<User>();
        q1.enqueue(new User(0, 20));
        q1.enqueue(new User(2, 30));
        q1.enqueue(new User(3, 40));
        q2.enqueue(new User(1, 0));
        q2.enqueue(new User(4, 10));

        merged = mergeTwoQueues("id", q1, q2);
        String mergeById = 
        "[ User ID: 0, Pages Printed: 20,\n  User ID: 1, Pages Printed: 0,\n  User ID: 2, Pages Printed: 30,\n  User ID: 3, Pages Printed: 40,\n  User ID: 4, Pages Printed: 10 ]";
        assertEquals(mergeById, merged.toString());        
    }

    @Test
    public void naiveMergeSortTest() {
        UserList list = new UserList();
        list.add(new User(2, 12));
        list.add(new User(0, 10));
        list.add(new User(1, 11));

        list.mergeSort("id");

        String sorted =
         "[ User ID: 0, Pages Printed: 10,\n  User ID: 1, Pages Printed: 11,\n  User ID: 2, Pages Printed: 12 ]";
        assertEquals(sorted, list.toString());

        list.mergeSort("pages");
        assertEquals(sorted, list.toString()); 

        UserList q1 = new UserList();
        q1.add(new User(0, 20));
        q1.add(new User(2, 30));
        q1.add(new User(3, 40));
        q1.add(new User(1, 0));
        q1.add(new User(4, 10));

        q1.mergeSort("page");
    }

    @Test
    public void naiveSortByBothTest() {
        UserList list = new UserList();
        list.add(new User(2, 12));
        list.add(new User(1, 10));
        list.add(new User(0, 10));
        list.add(new User(3, 10));
        list.add(new User(4, 10));
        list.add(new User(5, 10));
        list.add(new User(6, 10));

        list.sortByBothFeatures();

        // String sorted =
        //  "[ User ID: 0, Pages Printed: 10,\n  User ID: 1, Pages Printed: 10,\n  User ID: 2, Pages Printed: 12 ]";

        // assertEquals(sorted, list.toString());
        // System.out.println(list.toString());
    }

    public static void main(String [] args) {
        // Naive right-idea tests. Just because these tests pass does NOT mean
        // your code is bug-free!

        // Uncomment the following line when ready
         jh61b.junit.textui.runClasses(UserList.class);
    }

}
