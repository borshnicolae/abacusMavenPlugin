package com.bearingpoint.CustomMaven;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class MyView extends ViewPart {


    @Override
    public void createPartControl(Composite parent) {
    	new Comp(parent, SWT.NONE);
    	System.out.println("Hello World");
    }

    @Override
    public void setFocus() {
    }
    
}
