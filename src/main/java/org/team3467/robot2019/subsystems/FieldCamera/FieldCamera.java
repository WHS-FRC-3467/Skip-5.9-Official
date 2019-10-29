package org.team3467.robot2019.subsystems.FieldCamera;

import org.team3467.robot2019.subsystems.Limelight.Limelight;
import org.team3467.robot2019.subsystems.Limelight.Limelight.StreamMode;

//import org.opencv.core.Mat;

//import edu.wpi.cscore.CvSink;
//import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FieldCamera extends Subsystem {

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public FieldCamera() {
    
        //
        // Run Limelight Camera
        //
        // Default to Driver Mode
        Limelight.setDriverMode();

        // Place Secondary stream in lower right of Primary stream
        Limelight.setStreamMode(StreamMode.ePIPMain);


    	// Run one USB camera
    	 runOne();

    	// Run two USB cameras
    	// runTwo();
    }
    
    private void runOne() {
    	
		UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture("MS Lifecam Camera", 0);
        //camera1.setResolution(320, 240);
        //camera1.setFPS(15);
		camera1.setResolution(640, 480);
		camera1.setExposureAuto();
		camera1.setWhiteBalanceAuto();
        camera1.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        
        // These settings should be less than 1mbps streaming to Shuffleboard.
        // Make sure that you don’t change anything on the Shuffleboard side, just leave the defaults and
        // -1 as the compression so you don’t waste CPU cycles re-compressing the mjpg stream.
    }
    
 /*****
     private void runTwo() {
       	
     	Thread t = new Thread(() -> {
			
			boolean allowCam1 = true;
			
			UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(0);
	        camera1.setResolution(320, 240);
	        camera1.setFPS(15);
	        UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
	        camera2.setResolution(320, 240);
	        camera2.setFPS(20);
	        
	        CvSink cvSink = CameraServer.getInstance().getVideo(camera1);
	        CvSource outputStream = CameraServer.getInstance().putVideo("Switcher", 320, 240);
	        
	        Mat image = new Mat();
	        
	        while(!Thread.interrupted()) {
	        	
// TO-DO: Need OI method to get camera switch
//	        	if (CommandBase.oi.getGamepad().getRawButton(9)) {
//	        		allowCam1 = !allowCam1;
//	        	}
	        	
	            if (allowCam1){
	              cvSink.setSource(camera1);
	            } else{
	              cvSink.setSource(camera2);
	            }
	            
            	cvSink.grabFrame(image);
	            outputStream.putFrame(image);
	        }
	        
	    });

		t.start();
    }
******/

}

