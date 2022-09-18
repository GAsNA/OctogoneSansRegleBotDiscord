package org.gasna.learningBDiscord.utils.commands;

import org.gasna.learningBDiscord.commands.CommandOctogone;

public class MyThread implements Runnable {
    private Thread t;
    private final String threadName;

    public MyThread(String name) {
        threadName = name;
        //System.out.println("Creating " +  threadName );
    }

    public void run() {
        //System.out.println("Running " +  threadName );
        try {
            /*for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(5000);
            }*/
            MessageManager.octogoneActive = true;
            Thread.sleep(300000); // 5 minutes
            //Thread.sleep(5000); // 5 seconds
            //Thread.sleep(30000); // 30 seconds
            MessageManager.octogoneActive = false;
            CommandOctogone.finished();
        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        //System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        //System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}

/*class TestThread {

    public static void main(String args[]) {
        MyThread R1 = new MyThread( "Thread-1");
        R1.start();

        MyThread R2 = new MyThread( "Thread-2");
        R2.start();
    }
}*/