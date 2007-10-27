/*
 * ExampleEntryPoint.java
 *
 * Created on August 3, 2007, 3:51 PM
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.action.BindingAction;
import com.totsp.gwittir.client.fx.AnimationFinishedCallback;
import com.totsp.gwittir.client.fx.MutationStrategy;
import com.totsp.gwittir.client.fx.OpacityWrapper;
import com.totsp.gwittir.client.fx.PropertyAnimator;
import com.totsp.gwittir.client.fx.rebind.Dimensions;
import com.totsp.gwittir.client.fx.ui.ReflectedImage;
import com.totsp.gwittir.client.fx.ui.SoftAnimatedHorizontalScrollbar;
import com.totsp.gwittir.client.fx.ui.SoftAnimatedScrollbar;
import com.totsp.gwittir.client.fx.ui.SoftHorizontalScrollbar;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.fx.ui.SoftScrollArea;
import com.totsp.gwittir.client.fx.ui.SoftScrollbar;
import com.totsp.gwittir.client.keyboard.KeyBinding;
import com.totsp.gwittir.client.keyboard.KeyBindingEventListener;
import com.totsp.gwittir.client.keyboard.KeyboardController;
import com.totsp.gwittir.client.keyboard.SuggestedKeyBinding;
import com.totsp.gwittir.client.keyboard.Task;
import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;
import com.totsp.gwittir.client.ui.table.BoundTable;
import com.totsp.gwittir.client.ui.table.Field;
import com.totsp.gwittir.client.ui.table.GridForm;
import com.totsp.gwittir.client.ui.util.ChangeMarkedTypeFactory;
import com.totsp.gwittir.client.validator.DoubleValidator;
import com.totsp.gwittir.client.validator.IntegerValidator;
import com.totsp.gwittir.client.validator.PopupValidationFeedback;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class ExampleEntryPoint implements EntryPoint {
    
    boolean big = false;
    
    /** Creates a new instance of ExampleEntryPoint */
    public ExampleEntryPoint() {
    }
    
    public void onModuleLoad() {
        Logger log = Logger.getLogger("com.totsp.gwittir.example.client.ExampleEntryPoint");
        log.log( Level.ERROR, "startup", null);
        TabPanel tp = new TabPanel();
        
        final Button b = new Button("FOO!");
        
        final PropertyAnimator a = new PropertyAnimator(b, "height", "100px", "300px", MutationStrategy.UNITS_SINOIDAL, 3000, new AnimationFinishedCallback() {
            
            public void onFinish(PropertyAnimator animator) {
                final PropertyAnimator a = new PropertyAnimator(b, "height", "300px", "100px", MutationStrategy.UNITS_SINOIDAL, 3000);
                final PropertyAnimator a2 = new PropertyAnimator(b, "width", "300px", "100px", MutationStrategy.UNITS_SINOIDAL, 3000);
                a.start();
                a2.start();
            }
            
            public void onFailure(PropertyAnimator animator, Exception e) {
            }
        });
        final PropertyAnimator a2 = new PropertyAnimator(new OpacityWrapper(b), "opacity", new Double(0), new Double(1), MutationStrategy.DOUBLE_SINOIDAL, 3000);
        RootPanel.get().add(b);
        
        Button b2 = new Button("Animate", new ClickListener() {
            
            public void onClick(Widget w) {
                a.start();
                a2.start();
            }
        });
        RootPanel.get().add(b2);
        
        final Field[] mcf = new Field[9];
        mcf[0] = new Field("someInteger", "An Integer", null, "This is an Integer Value", null, IntegerValidator.INSTANCE, new PopupValidationFeedback(PopupValidationFeedback.BOTTOM));
        mcf[1] = new Field("name", "Name", null, "A name value <br /> who cares?");
        mcf[2] = new Field("firstName", "First Name", null, "Somebody's first name.");
        mcf[3] = new Field("lastName", "Last Name", null, "Somebody's last name.");
        mcf[4] = new Field("emailAddress", "Email Address", null, "Somebody's email.");
        
        mcf[6] = new Field("price", "Price", null, "This is an decimal Value", null, DoubleValidator.INSTANCE, new PopupValidationFeedback(PopupValidationFeedback.BOTTOM));
        mcf[7] = new Field("homeTown", "Home Town", null, "Somebody's place of origin.");
        mcf[8] = new Field("zipCode", "Postal Code", null, "A USPS Postal Code");
        
        final GridForm form = new GridForm(mcf, 3);
        form.setValue(new MyClass());
        
        GridForm form2 = new GridForm(mcf, 3);
        form2.setValue(form.getValue());
        
        VerticalPanel vp = new VerticalPanel();
        
        vp.add(form);
        
        vp.add(form2);
        
        tp.add(vp, "Grid Form");
        
        Button check = new Button("check", new ClickListener() {
            
            public void onClick(Widget sender) {
                
                Window.alert(((MyClass) form.getValue()).getPrice() + "");
            }
        });
        
        
        
        
        vp.add(check);
        
        vp = new VerticalPanel();
        final ReflectedImage ri = new ReflectedImage(GWT.getModuleBaseURL() + "gwtip.png", 163, 204, .2, .5);
        vp.add(ri);
        Button resize = new Button("resize", new ClickListener() {
            
            public void onClick(Widget sender) {
                PropertyAnimator a = new PropertyAnimator(ri, "width", new Integer(400), MutationStrategy.INTEGER_SINOIDAL);
                PropertyAnimator b = new PropertyAnimator(ri, "height", new Integer(400), MutationStrategy.INTEGER_SINOIDAL);
                a.start();
                b.start();
            }
        });
        
        vp.add(resize);
        
        tp.add(vp, "Reflected Image");
        DockPanel scroll = new DockPanel();
        
        
        
        final SoftScrollArea ssp = new SoftScrollArea();
        scroll.add(ssp, DockPanel.CENTER);
        ssp.setWidth("500px");
        ssp.setHeight("500px");
        vp = new VerticalPanel();
        vp.add(new Image(GWT.getModuleBaseURL() + "crested_butte.jpg"));
        final Button visible = new Button("ensure visible test");
        vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        vp.add(visible);
        
        vp.add(new Image(GWT.getModuleBaseURL() + "crested_butte.jpg"));
        vp.add(new Image(GWT.getModuleBaseURL() + "crested_butte.jpg"));
        vp.add(new Image(GWT.getModuleBaseURL() + "crested_butte.jpg"));
        
        
        ssp.setWidget(vp);
        
        SoftScrollbar bar = new SoftAnimatedScrollbar(ssp);
        //bar.setLowerWidget( new Image( GWT.getModuleBaseURL()+"gwtip.png") );
        //bar.setHigherWidget( new Image( GWT.getModuleBaseURL()+"gwtip.png") );
        //bar.setBarWidget( new Image( GWT.getModuleBaseURL()+"crested_butte.jpg") );
        bar.setHeight("400px");
        
        
        VerticalPanel control = new VerticalPanel();
        Button pageDown = new Button("down", new ClickListener() {
            
            public void onClick(Widget sender) {
                ssp.pageDownAnimated();
            }
        });
        pageDown.setSize("50px", "50px");
        Button pageUp = new Button("up", new ClickListener() {
            
            public void onClick(Widget sender) {
                ssp.pageUpAnimated();
            }
        });
        pageUp.setSize("50px", "50px");
        control.add(pageUp);
        control.add(bar);
        control.add(pageDown);
        scroll.add(control, DockPanel.EAST);
        
        HorizontalPanel hcontrol = new HorizontalPanel();
        SoftHorizontalScrollbar hbar = new SoftAnimatedHorizontalScrollbar(ssp);
        hbar.setWidth("400px");
        //hbar.setLowerWidget( new Image( GWT.getModuleBaseURL()+"gwtip.png") );
        //hbar.setHigherWidget( new Image( GWT.getModuleBaseURL()+"gwtip.png") );
        //hbar.setBarWidget( new Image( GWT.getModuleBaseURL()+"crested_butte.jpg") );
        Button pageLeft = new Button("<<", new ClickListener() {
            
            public void onClick(Widget sender) {
                ssp.pageLeftAnimated();
            }
        });
        pageLeft.setSize("50px", "50px");
        Button pageRight = new Button(">>", new ClickListener() {
            
            public void onClick(Widget sender) {
                ssp.pageRightAnimated();
            }
        });
        pageRight.setSize("50px", "50px");
        hcontrol.add(pageLeft);
        hcontrol.add(hbar);
        hcontrol.add(pageRight);
        scroll.add(hcontrol, DockPanel.SOUTH);
        
        
        
        
        
        
        
        Button checkEnsure = new Button("checkEnsure", new ClickListener() {
            
            public void onClick(Widget sender) {
                ssp.ensureVisibleAnimated(visible);
            }
        });
        vp = new VerticalPanel();
        vp.add(scroll);
        vp.add(checkEnsure);
        tp.add(vp, "Scroll Area");
        
        Field[] cols = new Field[5];
        for (int i = 0; i < 5; i++) {
            cols[i] = mcf[i];
        }
        VerticalPanel tablesPanel = new VerticalPanel();
        final BoundTable t = new BoundTable(BoundTable.HEADER_MASK + BoundTable.SORT_MASK
                + BoundTable.ROW_HANDLE_MASK + BoundTable.NO_SELECT_COL_MASK, new ChangeMarkedTypeFactory() , cols);
        ArrayList list = new ArrayList();
        list.add(form.getValue());
        list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );
        try{
            t.addKeyBinding( new SuggestedKeyBinding( 'N', true, false, false), new Task(){
                public void run() {
                    MyClass newClass =new MyClass();
                    t.add( newClass );
                    List select = new ArrayList();
                    select.add( newClass );
                    t.setSelected( select );
                }
            });
            t.addKeyBinding( new SuggestedKeyBinding( KeyBinding.DELETE, true, false, false), new Task(){
                public void run() {
                    List selected = t.getSelected();
                    List value = (List) t.getValue();
                    value.removeAll( selected );
                    t.setValue( value );
                }
                
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        // list.add( new MyClass() );
        //list.add( new MyClass() );
        t.setValue(list);
        tablesPanel.add( t );
        
        
        BoundTable t2 = new BoundTable(BoundTable.HEADER_MASK + BoundTable.SORT_MASK
                + BoundTable.ROW_HANDLE_MASK  + BoundTable.NO_SELECT_COL_MASK, cols);
        list = new ArrayList();
        list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );
        list.add( new MyClass() );
        t2.setValue( list );
        
        tablesPanel.add( t2 );
        tp.add(tablesPanel, "Bound Table");
        final Button alert = new Button("Alert Selection", new ClickListener(){
            public void onClick(final Widget e){
                alertSelection();
            }
        });
        tablesPanel.add( alert );
        
        RootPanel.get().add(tp);
        log.log(Level.DEBUG, Dimensions.INSTANCE.getMarginTop(tp.getElement()) + "", null);
        log.log(Level.DEBUG, tp.getOffsetHeight() + "", null);
        vp = new VerticalPanel();
        Button keyButton = new Button();
        keyButton.setHTML( "<u>S</u>ave" );
        keyButton.setKeyBinding( new KeyBinding('S', false, true, false ) );
        keyButton.setAction( new Action(){
            public void execute(BoundWidget model) {
                Window.alert("SAVE!");
            }
        });
        vp.add( keyButton );
        
        final Button suggest = new Button("Suggest");
        suggest.setAction(new BindingAction(){
            
            public void execute(BoundWidget model) {
                Window.alert("Suggested");
            }
            
            public void bind(BoundWidget widget) {
                KeyboardController.AutoMappedBinding auto = KeyboardController.INSTANCE.findSuggestedMapping( suggest.getText() );
                if( auto != null ){
                    suggest.setHTML( auto.newHtml );
                    suggest.setKeyBinding( auto.binding );
                    auto.binding.addKeyBindingEventListener( new KeyBindingEventListener(){
                        public void onUnbind(KeyBinding binding) {
                        }
                        
                        public void onBind(KeyBinding binding) {
                            KeyboardController.AutoMappedBinding auto = KeyboardController.INSTANCE.findSuggestedMapping( suggest.getText() );
                            if( auto != null ){
                                suggest.setHTML( auto.newHtml );
                                suggest.setKeyBinding( auto.binding );
                            } else {
                                suggest.setValue( suggest.getValue() );
                            }
                            
                        }
                        
                    });
                }
            }
            
            public void unbind(BoundWidget widget) {
            }
            
            public void set(BoundWidget widget) {
            }
        });
        
        vp.add( suggest );
        tp.add( vp, "Key Bindings");
        
        
    }
    
    private native void alertSelection()/*-{
        var selectionObject = $wnd.getSelection();
        var range = $doc.createRange();
                range.setStart(selectionObject.anchorNode,selectionObject.anchorOffset);
                range.setEnd(selectionObject.focusNode,selectionObject.focusOffset);
                var frag = range.cloneContents();
                for( x in frag ){
                  alert( x );
                }
                                         
    }-*/;
}