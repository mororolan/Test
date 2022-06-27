package team3.passpasspass.VM.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import team3.passpasspass.VM.controller.GUI.T3Panel;
import team3.passpasspass.VM.controller.GUI.TextFactory;
import team3.passpasspass.VM.controller.model.CheckKeyType;
import team3.passpasspass.VM.controller.model.ReadCSV;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class MachinerySimulatorPanelTest {

    private static MachinerySimulatorPanel MSP = new MachinerySimulatorPanel("VMCS - Machinery Panel", true);
    private static TextFactory warning = new TextFactory("No change");
    private static boolean loginStatus = true;
    private static AtomicBoolean unlockStatus = new AtomicBoolean(loginStatus);
    private static Random random = new Random();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void coinsChange() {
        ArrayList<String[]> coins = ReadCSV.readCSV("./data/dwd_money_stat.csv");

        unlockStatus.set(false);
        // can't change
        for (int i = 1; i < coins.size(); i++) {
            TextFactory a = new TextFactory(coins.get(i)[0]);
            JButton bnt = new JButton("Change Simulator");
            int finalI = i;
            int num = random.nextInt(30);
            bnt.setEnabled(unlockStatus.get());

            MSP.coinsChange(coins, unlockStatus, warning, finalI, num);
            System.out.println(warning.getText());
            assertEquals("The door is closed, you can't change the data",warning.getText());
        }

        unlockStatus.set(true);
        // can change & change successfully
        for (int i = 1; i < coins.size(); i++) {
            TextFactory a = new TextFactory(coins.get(i)[0]);
            JButton bnt = new JButton("Change Simulator");
            int finalI = i;
            int num = random.nextInt(40)+1;
            bnt.setEnabled(unlockStatus.get());

            MSP.coinsChange(coins, unlockStatus, warning, finalI, num);

            ArrayList<String[]> coinsNew = ReadCSV.readCSV("./data/dwd_money_stat.csv");
            String[] record = coinsNew.get(finalI);

            System.out.println("expected: "+ String.valueOf(num)+ ", actual: "+ record[1]);
            int actual = Integer.parseInt(record[1]);
            assertEquals(num,actual,0.0);
            assertEquals("Congratulations on your simulated modification of the number of coins",warning.getText());
        }

        // can change & change unsuccessfully
        unlockStatus.set(true);
        // can change & change successfully
        for (int i = 1; i < coins.size(); i++) {
            TextFactory a = new TextFactory(coins.get(i)[0]);
            JButton bnt = new JButton("Change Simulator");
            int finalI = i;
            int num = random.nextInt(30)+41;
            bnt.setEnabled(unlockStatus.get());

            MSP.coinsChange(coins, unlockStatus, warning, finalI, num);

            ArrayList<String[]> coinsNew = ReadCSV.readCSV("./data/dwd_money_stat.csv");
            String[] record = coinsNew.get(finalI);

            System.out.println("not expected: "+ String.valueOf(num)+ ", actual: "+ record[1]);
            int actual = Integer.parseInt(record[1]);
            assertNotEquals(num,actual,0.0);
            assertEquals("Wrong Input, please notify the range you can change is [0-40]",warning.getText());
        }
    }

    @Test
    public void cansChange() {
        ArrayList<String[]> cans = ReadCSV.readCSV("./data/dwd_drink_info.csv");

        unlockStatus.set(false);
        // can't change
        for (int i = 1; i < cans.size(); i++) {
            TextFactory a = new TextFactory(cans.get(i)[0]);
            JButton bnt = new JButton("Change Simulator");
            int finalI = i;
            int num = random.nextInt(15);
            bnt.setEnabled(unlockStatus.get());

            MSP.cansChange(cans, unlockStatus, warning, finalI, num);
            System.out.println(warning.getText());
            assertEquals("The door is closed, you can't change the data",warning.getText());
        }

        unlockStatus.set(true);
        // can change & change successfully
        for (int i = 1; i < cans.size(); i++) {
            TextFactory a = new TextFactory(cans.get(i)[0]);
            JButton bnt = new JButton("Change Simulator");
            int finalI = i;
            int num = random.nextInt(20)+1;
            bnt.setEnabled(unlockStatus.get());

            MSP.cansChange(cans, unlockStatus, warning, finalI, num);

            ArrayList<String[]> coinsNew = ReadCSV.readCSV("./data/dwd_drink_info.csv");
            String[] record = coinsNew.get(finalI);

            int actual = Integer.parseInt(record[2]);
            System.out.println("not expected: "+ String.valueOf(num)+ ", actual: "+ String.valueOf(actual));
            assertEquals(num,actual,0.0);
            assertEquals("Congratulations on your simulated modification of the number of cans",warning.getText());
        }

        // can change & change unsuccessfully
        unlockStatus.set(true);
        // can change & change successfully
        for (int i = 1; i < cans.size(); i++) {
            TextFactory a = new TextFactory(cans.get(i)[0]);
            JButton bnt = new JButton("Change Simulator");
            int finalI = i;
            int num = random.nextInt(30)+21;
            bnt.setEnabled(unlockStatus.get());

            MSP.cansChange(cans, unlockStatus, warning, finalI, num);

            ArrayList<String[]> coinsNew = ReadCSV.readCSV("./data/dwd_drink_info.csv");
            String[] record = coinsNew.get(finalI);

            int actual = Integer.parseInt(record[2]);
            System.out.println("not expected: "+ String.valueOf(num)+ ", actual: "+ String.valueOf(actual));
            assertNotEquals(num,actual,0.0);
            assertEquals("Wrong Input, please notify the range you can change is [0-20]",warning.getText());
        }
    }

    @Test
    public void changeUnlockStatus() {
        unlockStatus.set(true);
        MSP.changeUnlockStatus(unlockStatus);
        assertEquals(false,unlockStatus.get());
    }

    @Test
    public void openControllerPannel(){
        String actual = MSP.openControllerPannel(loginStatus);
        assertEquals("VMCS - Simulator Control Panel", actual);
    }
}