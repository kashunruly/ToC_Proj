import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;

public class LaunchPad extends JFrame  {

    int delay_counter = 0;
    int t_minus_counter = 10;
    int lightening_observed = 0;

    Random random = new Random();


    public LaunchPad() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Test Sound Clip");
        this.setSize(300, 200);
        this.setVisible(true);

        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource("female_countdown.mp3");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();


            Timer timer = new Timer(950,null);

            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    lightening_observed = random.nextInt(50) + 1;

                    if (lightening_observed > 10){

                        if (!clip.isRunning()){
                            clip.start();
                            delay_counter = 0;

                        }

                        System.out.println("timer: " + t_minus_counter);
                        t_minus_counter = t_minus_counter - 1;


                        if (t_minus_counter < 0){
                            timer.setRepeats(false);
                            timer.stop();

                            if (!clip.isRunning()){
                                clip.close();
                            }

                            System.out.println("lift off");

                        }

                    }else{

                        Timer new_timer = new Timer(3000,null);
                        lightening_observed = 0;
                        delay_counter = 0;
                        clip.stop();
                        new_timer.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                delay_counter = delay_counter + 1;

                                lightening_observed = random.nextInt(50) + 1;

                                if (delay_counter >= 3){

                                    delay_counter = -5;
                                    lightening_observed = -5;
                                    System.out.println("30 minutes passed");
                                    new_timer.setRepeats(false);
                                    new_timer.stop();

                                }else{

                                    if (lightening_observed <= 10 && lightening_observed > -1){

                                        delay_counter = -5;
                                        lightening_observed = -5;
                                        System.out.println("Abort");
                                        clip.stop();
                                        clip.close();
                                        timer.setRepeats(false);
                                        timer.stop();
                                        new_timer.setRepeats(false);
                                        new_timer.stop();


                                    }else{

                                        if (delay_counter > 0){

                                            System.out.println("delayed for: " + delay_counter + " seconds");

                                        }

                                    }

                                }
                            }
                        });

                        new_timer.setRepeats(true);
                        new_timer.start();


                    }

                }
            });

            timer.setRepeats(true);
            timer.start();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new LaunchPad();



    }

}
