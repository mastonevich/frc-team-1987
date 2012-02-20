/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.L_Targeting_Target;

/**
 *
 * @author team1987
 */
public class Targeting extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new L_Targeting_Target());
    }
    
    public Targeting(){
        camera = AxisCamera.getInstance();  // get an instance ofthe camera
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
        cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
    }
    
    public void target()
    {
        
        System.out.println("+++++++++++++TARGETING+++++++++++++");
        double bestTgtX = 0, bestTgtY = -1;
        
        try {
                /**
                 * Do the image capture with the camera and apply the algorithm
                 * described above. This sample will either get images from the
                 * camera or from an image file stored in the top level
                 * directory in the flash memory on the cRIO. The file name in
                 * this case is "10ft2.jpg"
                 *
                 */
                ColorImage image = null;
                try {
                    image = camera.getImage(); // comment if using stored images
                } catch (AxisCameraException ex) {
                    ex.printStackTrace();
                } catch (NIVisionException ex) {
                    ex.printStackTrace();
                }
                //ColorImage image;                           // next 2 lines read image from flash on cRIO
                //image =  new RGBImage("/10ft2.jpg");
                BinaryImage thresholdImage = image.thresholdRGB(65, 160, 225, 255, 250, 255);   // keep only red objects
                BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);  // remove small artifacts
                BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
                BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // find filled in rectangles

                ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
//                for (int i = 0; i < reports.length; i++) {                                // print results
//                    ParticleAnalysisReport r = reports[i];
                    
                    /*
                    System.out.println("center_mass_x_normalized " + r.center_mass_x_normalized);
                    System.out.println("center_mass_y " + r.center_mass_y);
                    System.out.println("center_mass_y_normalized " + r.center_mass_y_normalized);
                    System.out.println("imageHeight " + r.imageHeight);
                    System.out.println("imageWidth " + r.imageWidth);
                    System.out.println("particleArea " + r.particleArea);
                    System.out.println("particleQuality " + r.particleQuality);
                    System.out.println("particleToImagePercent " + r.particleToImagePercent);
                    */
//                }

                for(int i = 0; i < reports.length; i++)
                {
                    ParticleAnalysisReport r = reports[i];
                    if(r.center_mass_y_normalized > bestTgtY)
                    {
                        System.out.println("Rectangle Width " + r.boundingRectWidth);
                        bestTgtY = r.center_mass_y_normalized;
                        bestTgtX = r.center_mass_x_normalized;
                    }
                }
                //Rotate chassis 
                
                System.out.println(filteredImage.getNumberParticles() + "  " + Timer.getFPGATimestamp());

                /**
                 * all images in Java must be freed after they are used since
                 * they are allocated out of C data structures. Not calling
                 * free() will cause the memory to accumulate over each pass of
                 * this loop.
                 */
                filteredImage.free();
                convexHullImage.free();
                bigObjectsImage.free();
                thresholdImage.free();
                image.free();

//            } catch (AxisCameraException ex) {        // this is needed if the camera.getImage() is called
//                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
    }
}
