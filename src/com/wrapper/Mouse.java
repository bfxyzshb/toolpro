package com.wrapper;

public class Mouse implements ComputerInterface{
    ComputerInterface computerInterface;
    Mouse(ComputerInterface computerInterface){
        this.computerInterface=computerInterface;
    }

    @Override
    public void echo() {
        System.out.println("添加鼠标...");
        computerInterface.echo();
    }
}
