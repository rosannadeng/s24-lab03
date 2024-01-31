package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(3);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        Integer expectedElement = 5; // Example value
        mQueue.enqueue(expectedElement);
        // Assert: Check if the peeked element is as expected
        assertEquals(expectedElement, mQueue.peek());
    
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        Integer firstEle = 10;
        Integer secondEle = 20;
        mQueue.enqueue(firstEle);
        mQueue.enqueue(secondEle);
        assertEquals(firstEle, mQueue.dequeue());
        assertEquals(secondEle,mQueue.dequeue());
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testClear() {
        // Pre-Clear: Add some elements to the queue
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.enqueue(3);

        mQueue.clear();

        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
        assertNull(mQueue.peek());
    }

    @Test
    public void testEnsureCapacity(){
        int maxCapacity = 10;

        for (int i = 0; i<maxCapacity;i++){
            mQueue.enqueue(i);
        }
        mQueue.dequeue();
        mQueue.enqueue(maxCapacity); //overflow test
        mQueue.enqueue(maxCapacity+1);
        assertEquals(maxCapacity+1, mQueue.size());

        for (int i = 0; i <= maxCapacity; i++) {
            assertEquals("Dequeued element should match", Integer.valueOf(i+1), mQueue.dequeue());
        }
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }


}
