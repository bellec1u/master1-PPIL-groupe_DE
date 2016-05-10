package com.ppil.groupede.callmeishmael.data;

/**
 * Created by Pima on 10/05/16.
 */
public class TestData {

    public static void main(String[] args)
    {
        DataManager dm = new DataManager();
        String url = DataManager.urlLogin;
        //dm.setUrlLogin("A","B");
        dm.setUrlBookDetail(1);
        System.out.println("URL : "+dm.getAdresseDesti());
        dm.run();
        String res = dm.getResult();
        System.out.println(res);
    }
}
