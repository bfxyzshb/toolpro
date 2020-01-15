package com.wrapper;

public class Keyboard implements ComputerInterface{
    ComputerInterface computerInterface;

    Keyboard(ComputerInterface computerInterface){
        this.computerInterface=computerInterface;
    }


    @Override
    public void echo() {
        System.out.println("添加键盘...");
        computerInterface.echo();

    }
}
