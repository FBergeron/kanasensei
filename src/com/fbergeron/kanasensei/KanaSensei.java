/*
 * Copyright (C) 1999  Frédéric Bergeron (fbergeron@users.sourceforge.net)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.fbergeron.kanasensei;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import com.fbergeron.util.*;

/** Small applet to learn Japanese kana.
 * @author Frederic Bergeron
 * @author <a href="http://www.fbergeron.com">http://www.fbergeron.com</a>
 * @version Version 1.2
 */
public class KanaSensei extends Frame
{

	/**
	 * Constructs a KanaSensei frame.
	 *
	 * @param applet Applet from which the frame is invoked.
	 * @param title Title of the KanaSensei frame.
	 * @param locale Locale used for KanaSensei.
	 */
	public KanaSensei( java.applet.Applet applet )
	{
		super();
		this.applet = applet;

        isAllKanas = true;
		isLoopTest = false;
		testType = KATAKANA_ROMAJI;
		hasSound = true;

        kanaSetA = new Toggle( true );
        kanaSetK = new Toggle( false );
        kanaSetS = new Toggle( false );
        kanaSetT = new Toggle( false );
        kanaSetN = new Toggle( false );
        kanaSetH = new Toggle( false );
        kanaSetM = new Toggle( false );
        kanaSetY = new Toggle( false );
        kanaSetR = new Toggle( false );
        kanaSetW = new Toggle( false );
        kanaSetG = new Toggle( false );
        kanaSetZ = new Toggle( false );
        kanaSetD = new Toggle( false );
        kanaSetB = new Toggle( false );
        kanaSetP = new Toggle( false );

		buildGUI();
		newTest();
    }

    /**
     * Shows or hides the component depending on the boolean flag b.
     * @param b  if true, show the component; otherwise, hide the component.
     * @see java.awt.Component#isVisible
     */
    public void setVisible(boolean b)
	{
	    Dimension scrSize = getToolkit().getScreenSize();
	    Dimension size = getSize();
		if(b)
			setLocation( (scrSize.width - size.width) / 2, (scrSize.height - size.height) / 2 );
		super.setVisible(b);
	}

    /**
     * Sets the locale.
     * @param locale Locale.
     */
    public void setLocale( Locale locale ) {
        super.setLocale( locale );
        
		resBundle = ResourceBundle.getBundle( KanaSensei.class.getName() + "Ress", locale );

        menuOptions.setLabel( resBundle.getString( "Options" ) );
		menuItemLoopTest.setLabel( resBundle.getString( "LoopTest" ) );
		menuItemAllKanas.setLabel( resBundle.getString( "AllKanas" ) );
		menuItemNewTest.setLabel( resBundle.getString( "NewTest" ) );
		menuItemHasSound.setLabel( resBundle.getString( "HasSound" ) );
		menuViews.setLabel( resBundle.getString( "Views" ) );
		menuItemRomaKata.setLabel( resBundle.getString( "RomaAndKata" ) );
		menuItemRomaHira.setLabel( resBundle.getString( "RomaAndHira" ) );
		menuItemHira.setLabel( resBundle.getString( "HiraAndRoma" ) );
		menuItemKata.setLabel( resBundle.getString( "KataAndRoma" ) );
        menuLanguage.setLabel( resBundle.getString( "Language" ) );
        menuItemEnglish.setLabel( resBundle.getString( "English" ) );
        menuItemFrench.setLabel( resBundle.getString( "French" ) );
        menuItemSpanish.setLabel( resBundle.getString( "Spanish" ) );
        menuItemGerman.setLabel( resBundle.getString( "German" ) );
        menuItemItalian.setLabel( resBundle.getString( "Italian" ) );
		menuHelp.setLabel( resBundle.getString( "Help" ) );
		menuItemHelp.setLabel( resBundle.getString( "Directions" ) );
		menuItemAbout.setLabel( resBundle.getString( "About" ) );
        menuItemLicense.setLabel( resBundle.getString( "License" ) );
	    setTitle( resBundle.getString( "KanaSensei" ) );
        scoreBoard.setLocale( locale );
        feedback.setLocale( locale );
    }
    
    /**
     * Updates the KanaSensei frame.
     */
    protected void update() {
        updateMenus();
        keyboard.update();
        screen.repaint();
    }

    /**
     * Starts a new test.
     */
    protected void newTest() {
        rightAnswers = 0;
        wrongAnswers = 0;
        scoreBoard.repaint();

        updateKanaPool();
        keyboard.unhighlightKana();
        ask();
    }

	/**
	 * Asks a new kana.
	 */
	protected void askAnotherKana() {
        if( !isLoopTest )
            kanaPool.removeElement( currKana );
        if( !isLoopTest && kanaPool.isEmpty() )
            conclude();
        else
            ask();
    }

    /**
     * Tells if the answer is right or wrong using sounds if the option
     * is enabled.
     *
     * @param kanaAnswered Answer given by user.
     */
    protected void doCorrection( Kana kanaAnswered ) {
        if( currKana == kanaAnswered )
            rightAnswer();
        else
            wrongAnswer();
    }

    /**
     * Updates pool of kanas to ask.
     * Each time a kana is asked, it is removed from the pool.
     * The test is over when the pool of kanas is empty.
     * When a new test begins, the pool is reset according to
     * the selected kanas.
     */
    protected void updateKanaPool() {
        if( kanaPool == null )
            kanaPool = new Vector();

        String[] tmpSet = null;

        tmpSet = new String[] { "a", "i", "u", "e", "o" };
        if( kanaSetA.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "ka", "ki", "ku", "ke", "ko" };
        if( kanaSetK.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "sa", "shi", "su", "se", "so" };
        if( kanaSetS.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "ta", "ti", "tsu", "te", "to" };
        if( kanaSetT.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "na", "ni", "nu", "ne", "no", "n" };
        if( kanaSetN.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "ha", "hi", "fu", "he", "ho" };
        if( kanaSetH.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "ma", "mi", "mu", "me", "mo" };
        if( kanaSetM.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "ya", "yu", "yo" };
        if( kanaSetY.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "ra", "ri", "ru", "re", "ro" };
        if( kanaSetR.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "wa", "o*" };
        if( kanaSetW.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "ga", "gi", "gu", "ge", "go" };
        if( kanaSetG.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "za", "ji", "zu", "ze", "zo" };
        if( kanaSetZ.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "da", "ji*", "zu*", "de", "do" };
        if( kanaSetD.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "ba", "bi", "bu", "be", "bo" };
        if( kanaSetB.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );

        tmpSet = new String[] { "pa", "pi", "pu", "pe", "po" };
        if( kanaSetP.isOn() || isAllKanas ) insertKanaIntoPool( tmpSet ); else removeKanaFromPool( tmpSet );
    }

    private void insertKanaIntoPool( String[] kanas ) {
        for( int i = 0; i < kanas.length; i++ ) {
            Kana k = Kana.get( kanas[ i ] );
            if( !kanaPool.contains( k ) )
                kanaPool.addElement( k );
        }
    }

    private void removeKanaFromPool( String[] kanas ) {
        for( int i = 0; i < kanas.length; i++ ) {
            Kana k = Kana.get( kanas[ i ] );
            if( kanaPool.contains( k ) )
                kanaPool.removeElement( k );
        }
    }

    private void buildGUI() {
		setBackground( Color.gray );
		setLayout( new BorderLayout() );

		panelOutput = new Panel( new GridLayout( 1, 2 ) );

		screen = new Screen();
		screen.setBackground( Color.white );
		scoreBoard = new ScoreBoard();
		scoreBoard.setBackground( new Color( 205, 205, 154 ) );
		scoreBoard.setFont( new Font( "Arial", Font.BOLD, 24 ) );

		panelOutput.add( screen );
		panelOutput.add( scoreBoard );

		panelConsole = new Panel( new GridLayout( 2, 1 ) );
        keyboard = new Keyboard();
        keyboard.setBackground( new Color( 100, 100, 255 ) );
        panelConsole.add( panelOutput );
        panelConsole.add( keyboard );

        feedback = new Feedback();
        feedback.setFont( new Font( "Arial", Font.BOLD, 16 ) );
        feedback.setBackground( new Color( 200, 200, 255 ) );

        add( "Center", panelConsole );
        add( "South", feedback );

        class TestTypeListener implements ItemListener {
            public TestTypeListener( int correspondingTestType ) {
                this.correspondingTestType = correspondingTestType;
            }

            public void itemStateChanged( ItemEvent e ) {
                testType = correspondingTestType;
                update();
                KanaSensei.this.invalidate();
                KanaSensei.this.validate();
            }

            int correspondingTestType;
        }

        class LocaleListener implements ItemListener {
            public LocaleListener( Locale locale ) {
                this.locale = locale;
            }

            public void itemStateChanged( ItemEvent e ) {
                KanaSensei.this.setLocale( locale );
            }

            Locale locale;;
        }

		//Menus
		menubar = new MenuBar();
		setMenuBar( menubar );

		//Menu Options
		menuOptions = new Menu();
		menubar.add( menuOptions );

		menuItemLoopTest = new CheckboxMenuItem();
		menuItemLoopTest.addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent e ) {
                isLoopTest = !isLoopTest;
                updateMenus();
                newTest();
            }
		});

		menuItemAllKanas = new CheckboxMenuItem();
		menuItemAllKanas.addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent e ) {
                isAllKanas = !isAllKanas;
                newTest();
            }
		});

		menuItemNewTest = new MenuItem();
		menuItemNewTest.addActionListener( new NewTestListener() );

		menuItemHasSound = new CheckboxMenuItem( null, hasSound );
		menuItemHasSound.addItemListener( new ItemListener() {
		    public void itemStateChanged( ItemEvent e ) {
		        hasSound = !hasSound;
		    }
		} );

        menuItemEnglish = new CheckboxMenuItem();
        menuItemEnglish.addItemListener( new LocaleListener( Locale.ENGLISH ) );
        menuItemFrench = new CheckboxMenuItem();
        menuItemFrench.addItemListener( new LocaleListener( Locale.FRENCH ) );
        menuItemSpanish= new CheckboxMenuItem();
        menuItemSpanish.addItemListener( new LocaleListener( new Locale( "es", "" ) ) );
        menuItemGerman = new CheckboxMenuItem();
        menuItemGerman.addItemListener( new LocaleListener( Locale.GERMAN ) );
        menuItemItalian = new CheckboxMenuItem();
        menuItemItalian.addItemListener( new LocaleListener( Locale.ITALIAN ) );
        
        menuLanguage = new Menu();
        menuLanguage.add( menuItemEnglish );
        menuLanguage.add( menuItemFrench );
        menuLanguage.add( menuItemSpanish );
        menuLanguage.add( menuItemGerman );
        menuLanguage.add( menuItemItalian );

		menuOptions.add( menuItemNewTest );
		menuOptions.add( new MenuItem( "-" ) );
		menuOptions.add( menuItemAllKanas );
		menuOptions.add( new MenuItem( "-" ) );
		menuOptions.add( menuItemLoopTest );
		menuOptions.add( new MenuItem( "-" ) );
		menuOptions.add( menuItemHasSound );
        menuOptions.add( menuLanguage );

        //Menu Views
		menuViews = new Menu();
		menubar.add( menuViews );

		menuItemRomaKata = new CheckboxMenuItem();
		menuItemRomaKata.addItemListener( new TestTypeListener( ROMAJI_KATAKANA ) );
		menuItemRomaHira = new CheckboxMenuItem();
		menuItemRomaHira.addItemListener( new TestTypeListener( ROMAJI_HIRAGANA ) );
		menuItemHira = new CheckboxMenuItem();
		menuItemHira.addItemListener( new TestTypeListener( HIRAGANA_ROMAJI ) );
		menuItemKata = new CheckboxMenuItem();
		menuItemKata.addItemListener( new TestTypeListener( KATAKANA_ROMAJI ) );

		menuViews.add( menuItemKata );
		menuViews.add( menuItemHira );
		menuViews.add( menuItemRomaKata );
		menuViews.add( menuItemRomaHira );

		//Menu Help
		menuHelp = new Menu();
		menubar.add( menuHelp );
		menuItemHelp = new MenuItem();
		menuItemHelp.addActionListener( new HelpListener() );
		menuItemAbout = new MenuItem();
		menuItemAbout.addActionListener( new AboutListener() );
        menuItemLicense = new MenuItem();
        menuItemLicense.addActionListener( new LicenseListener() );
		menuHelp.add( menuItemHelp );
		menuHelp.add( new MenuItem( "-" ) );
		menuHelp.add( menuItemAbout );
        menuHelp.add( menuItemLicense );

		addWindowListener( new WindowManager( this, 
            applet == null ? WindowManager.EXIT_ON_CLOSE : WindowManager.DISPOSE_ON_CLOSE ) );

		setSize( 800, 600 );
        update();
	    setVisible( true );
    }

    private void ask() {
        if( kanaPool.isEmpty() ) {
            currKana = null;
            String title = resBundle.getString( "Warning" );
            String msg = resBundle.getString( "KanaPoolEmpty" );
            DialogMsg d = new DialogMsg( this, title, true, msg );
            d.setVisible( true );
        }
        else {
            state = ASKING_KANA;
            currKana = (Kana)( kanaPool.elementAt( (int)( Math.random() * kanaPool.size() ) ) );
            feedback.update();
            attempts = 0;
            screen.repaint();
        }
    }

	private void rightAnswer() {
	    if( applet != null && hasSound )
	        applet.play( applet.getCodeBase(), "com/fbergeron/kanasensei/right.au" );
	    if( attempts == 0 )
            rightAnswers++;
        scoreBoard.repaint();
        askAnotherKana();
	}

	private void wrongAnswer() {
        state = WRONG_KANA;
	    if( applet != null && hasSound )
	        applet.play( applet.getCodeBase(), "com/fbergeron/kanasensei/wrong.au" );
        feedback.update();
	    attempts++;
        if( attempts < MAX_ATTEMPTS ) {
            wrongAnswers++;
            scoreBoard.repaint();
        }
	    if( attempts == MAX_ATTEMPTS )
	        showRightAnswer();
	}

	private void showRightAnswer() {
        state = KanaSensei.RIGHT_KANA;
        feedback.update();
        keyboard.hightlightKana( currKana );
    }

	private void conclude() {
        String title = resBundle.getString( "Message" );
        String msg = resBundle.getString( "TestConcluded" );
        DialogMsg d = new DialogMsg( this, title, true, msg );
        d.setVisible( true );
        newTest();
	}

    private void updateMenus() {
        menuItemRomaKata.setState( testType == ROMAJI_KATAKANA );
        menuItemRomaHira.setState( testType == ROMAJI_HIRAGANA );
        menuItemKata.setState( testType == KATAKANA_ROMAJI );
        menuItemHira.setState( testType == HIRAGANA_ROMAJI );

        menuItemAllKanas.setState( isAllKanas );
        menuItemLoopTest.setState( isLoopTest );
    }

    class AboutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Locale locale = KanaSensei.this.getLocale();
            DateFormat df = DateFormat.getDateInstance( DateFormat.LONG, locale );
            Calendar cal = Calendar.getInstance();
            cal.set( 2002, 3, 2 );
            FrameAbout frameAbout = new FrameAbout( resBundle.getString( "KanaSensei" ), "1.3",
                "Frédéric Bergeron", "FBergeron@users.sourceforge.net",
                    df.format( cal.getTime() ), locale );
            frameAbout.setVisible( true );
        }
    }

    class LicenseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String title = resBundle.getString( "License" );
            String msg = resBundle.getString( "LicenseText" );
            DialogMsg licenseWindow = new DialogMsg( KanaSensei.this, title, true, msg );
            licenseWindow.setLocation( 20, 20 );
            licenseWindow.setSize( 500, 300 );
            licenseWindow.setFont( new Font( "Arial", Font.PLAIN, 14 ) );
            licenseWindow.setResizable( true );
            licenseWindow.setVisible( true );
        }
    }

	class NewTestListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            newTest();
        }
    }

	class HelpListener implements ActionListener {
        public void actionPerformed( ActionEvent e ) {
            String title = resBundle.getString( "Help" );
            String msg = resBundle.getString( "HelpText" );
            DialogMsg helpWindow = new DialogMsg( KanaSensei.this, title, true, msg );
            helpWindow.setLocation( 10, 10 );
            helpWindow.setSize( 400, 500 );
            helpWindow.setFont( new Font( "Arial", Font.PLAIN, 14 ) );
            helpWindow.setResizable( true );
            helpWindow.setVisible( true );
        }
    }

	class KanaListener extends MouseAdapter {
	    public KanaListener( KanaButton kanaButton ) {
	        this.kanaButton = kanaButton;
	    }

        public void mousePressed( MouseEvent e ) {
            if( kanaButton.isHighlighted() ) {
                kanaButton.unhighlight();
                askAnotherKana();
            }
            else if( attempts < MAX_ATTEMPTS )
                doCorrection( kanaButton.kana );
        }

        private KanaButton kanaButton;
    }

    /**
     * Panel on which results of the test are displayed.
     */

    class ScoreBoard extends Panel {
        public ScoreBoard() {
            this.setLayout( new GridLayout( 3, 1, 20, 0 ) );

            labelRightAnswers = new Label( "Rights", Label.LEFT );
            labelRightAnswersCnt = new Label( "0", Label.RIGHT );
            labelWrongAnswers = new Label( "Wrongs", Label.LEFT );
            labelWrongAnswersCnt = new Label( "0", Label.RIGHT );
            labelTotal = new Label( "Total", Label.LEFT );
            labelTotalCnt = new Label( "0", Label.RIGHT );

            panelRightAnswers = new Panel( new BorderLayout() );
            panelWrongAnswers = new Panel( new BorderLayout() );
            panelTotal = new Panel( new BorderLayout() );

            panelRightAnswers.add( BorderLayout.CENTER, labelRightAnswers );
            panelRightAnswers.add( BorderLayout.EAST, labelRightAnswersCnt );

            panelWrongAnswers.add( BorderLayout.CENTER, labelWrongAnswers );
            panelWrongAnswers.add( BorderLayout.EAST, labelWrongAnswersCnt );

            panelTotal.add( BorderLayout.CENTER, labelTotal );
            panelTotal.add( BorderLayout.EAST, labelTotalCnt );

            this.add( panelRightAnswers );
            this.add( panelWrongAnswers );
            this.add( panelTotal );
        }

        public void setLocale( Locale locale ) {
            super.setLocale( locale );
            labelRightAnswers.setText( resBundle.getString( "Rights" ) );
            labelWrongAnswers.setText( resBundle.getString( "Wrongs" ) );
            labelTotal.setText( resBundle.getString( "Total" ) );
        }

        public Insets getInsets() {
            return( new Insets( 40, 10, 40, 10 ) );
        }

        public void paint( Graphics g ) {
            super.paint( g );
            labelRightAnswersCnt.setText( new Integer( rightAnswers ).toString() );
            labelWrongAnswersCnt.setText( new Integer( wrongAnswers ).toString() );
            labelTotalCnt.setText( new Integer( rightAnswers + wrongAnswers ).toString() );
        }

        private Label labelRightAnswers;
        private Label labelRightAnswersCnt;
        private Label labelWrongAnswers;
        private Label labelWrongAnswersCnt;
        private Label labelTotal;
        private Label labelTotalCnt;
        private Panel panelRightAnswers;
        private Panel panelWrongAnswers;
        private Panel panelTotal;
    }

    class KanaCheckboxListener implements ItemListener {
        public KanaCheckboxListener( Toggle kanaSet ) {
            this.kanaSet = kanaSet;
        }

        public void itemStateChanged( ItemEvent e ) {
            kanaSet.toggle();
            if( !isAllKanas ) newTest();
        }

        private Toggle kanaSet;
    }

    /**
     * Virtual keyboard of kanas.
     */

    class Keyboard extends Panel {
        public Keyboard() {
            this.setLayout( new GridLayout( 7, 15, 0, 0 ) );

            checkboxA = new Checkbox(); this.add( checkboxA ); checkboxA.setState( kanaSetA.isOn() );
            checkboxA.addItemListener( new KanaCheckboxListener( kanaSetA ) );

            checkboxK = new Checkbox(); this.add( checkboxK );  checkboxK.setState( kanaSetK.isOn() );
            checkboxK.addItemListener( new KanaCheckboxListener( kanaSetK ) );

            checkboxS = new Checkbox(); this.add( checkboxS );  checkboxS.setState( kanaSetS.isOn() );
            checkboxS.addItemListener( new KanaCheckboxListener( kanaSetS ) );

            checkboxT = new Checkbox(); this.add( checkboxT );  checkboxT.setState( kanaSetT.isOn() );
            checkboxT.addItemListener( new KanaCheckboxListener( kanaSetT ) );

            checkboxN = new Checkbox(); this.add( checkboxN );  checkboxN.setState( kanaSetN.isOn() );
            checkboxN.addItemListener( new KanaCheckboxListener( kanaSetN ) );

            checkboxH = new Checkbox(); this.add( checkboxH );  checkboxH.setState( kanaSetH.isOn() );
            checkboxH.addItemListener( new KanaCheckboxListener( kanaSetH ) );

            checkboxM = new Checkbox(); this.add( checkboxM );  checkboxM.setState( kanaSetM.isOn() );
            checkboxM.addItemListener( new KanaCheckboxListener( kanaSetM ) );

            checkboxY = new Checkbox(); this.add( checkboxY );  checkboxY.setState( kanaSetY.isOn() );
            checkboxY.addItemListener( new KanaCheckboxListener( kanaSetY ) );

            checkboxR = new Checkbox(); this.add( checkboxR );  checkboxR.setState( kanaSetR.isOn() );
            checkboxR.addItemListener( new KanaCheckboxListener( kanaSetR ) );

            checkboxW = new Checkbox(); this.add( checkboxW );  checkboxW.setState( kanaSetW.isOn() );
            checkboxW.addItemListener( new KanaCheckboxListener( kanaSetW ) );

            checkboxG = new Checkbox(); this.add( checkboxG );  checkboxG.setState( kanaSetG.isOn() );
            checkboxG.addItemListener( new KanaCheckboxListener( kanaSetG ) );

            checkboxZ = new Checkbox(); this.add( checkboxZ ); checkboxZ.setState( kanaSetZ.isOn() );
            checkboxZ.addItemListener( new KanaCheckboxListener( kanaSetZ ) );

            checkboxD = new Checkbox(); this.add( checkboxD );  checkboxD.setState( kanaSetD.isOn() );
            checkboxD.addItemListener( new KanaCheckboxListener( kanaSetD ) );

            checkboxB = new Checkbox(); this.add( checkboxB );  checkboxB.setState( kanaSetB.isOn() );
            checkboxB.addItemListener( new KanaCheckboxListener( kanaSetB ) );

            checkboxP = new Checkbox(); this.add( checkboxP );  checkboxP.setState( kanaSetP.isOn() );
            checkboxP.addItemListener( new KanaCheckboxListener( kanaSetP ) );

            kanaButtonA = new KanaButton( Kana.get( "a" ) ); this.add( kanaButtonA );
            kanaButtonKA = new KanaButton( Kana.get( "ka" ) ); this.add( kanaButtonKA );
            kanaButtonSA = new KanaButton( Kana.get( "sa" ) ); this.add( kanaButtonSA );
            kanaButtonTA = new KanaButton( Kana.get( "ta" ) ); this.add( kanaButtonTA );
            kanaButtonNA = new KanaButton( Kana.get( "na" ) ); this.add( kanaButtonNA );
            kanaButtonHA = new KanaButton( Kana.get( "ha" ) ); this.add( kanaButtonHA );
            kanaButtonMA = new KanaButton( Kana.get( "ma" ) ); this.add( kanaButtonMA );
            kanaButtonYA = new KanaButton( Kana.get( "ya" ) ); this.add( kanaButtonYA );
            kanaButtonRA = new KanaButton( Kana.get( "ra" ) ); this.add( kanaButtonRA );
            kanaButtonWA = new KanaButton( Kana.get( "wa" ) ); this.add( kanaButtonWA );
            kanaButtonGA = new KanaButton( Kana.get( "ga" ) ); this.add( kanaButtonGA );
            kanaButtonZA = new KanaButton( Kana.get( "za" ) ); this.add( kanaButtonZA );
            kanaButtonDA = new KanaButton( Kana.get( "da" ) ); this.add( kanaButtonDA );
            kanaButtonBA = new KanaButton( Kana.get( "ba" ) ); this.add( kanaButtonBA );
            kanaButtonPA = new KanaButton( Kana.get( "pa" ) ); this.add( kanaButtonPA );

            kanaButtonI = new KanaButton( Kana.get( "i" ) ); this.add( kanaButtonI );
            kanaButtonKI = new KanaButton( Kana.get( "ki" ) ); this.add( kanaButtonKI );
            kanaButtonSI = new KanaButton( Kana.get( "shi" ) ); this.add( kanaButtonSI );
            kanaButtonTI = new KanaButton( Kana.get( "ti" ) ); this.add( kanaButtonTI );
            kanaButtonNI = new KanaButton( Kana.get( "ni" ) ); this.add( kanaButtonNI );
            kanaButtonHI = new KanaButton( Kana.get( "hi" ) ); this.add( kanaButtonHI );
            kanaButtonMI = new KanaButton( Kana.get( "mi" ) ); this.add( kanaButtonMI );
            this.add( new Panel() );
            kanaButtonRI = new KanaButton( Kana.get( "ri" ) ); this.add( kanaButtonRI );
            this.add( new Panel() ); //kanaButtonWI = new KanaButton( Kana.get( "wi" ) ); this.add( kanaButtonWI );
            kanaButtonGI = new KanaButton( Kana.get( "gi" ) ); this.add( kanaButtonGI );
            kanaButtonZI = new KanaButton( Kana.get( "ji" ) ); this.add( kanaButtonZI );
            kanaButtonDI = new KanaButton( Kana.get( "ji*" ) ); this.add( kanaButtonDI );
            kanaButtonBI = new KanaButton( Kana.get( "bi" ) ); this.add( kanaButtonBI );
            kanaButtonPI = new KanaButton( Kana.get( "pi" ) ); this.add( kanaButtonPI );

            kanaButtonU = new KanaButton( Kana.get( "u" ) ); this.add( kanaButtonU );
            kanaButtonKU = new KanaButton( Kana.get( "ku" ) ); this.add( kanaButtonKU );
            kanaButtonSU = new KanaButton( Kana.get( "su" ) ); this.add( kanaButtonSU );
            kanaButtonTU = new KanaButton( Kana.get( "tsu" ) ); this.add( kanaButtonTU );
            kanaButtonNU = new KanaButton( Kana.get( "nu" ) ); this.add( kanaButtonNU );
            kanaButtonHU = new KanaButton( Kana.get( "fu" ) ); this.add( kanaButtonHU );
            kanaButtonMU = new KanaButton( Kana.get( "mu" ) ); this.add( kanaButtonMU );
            kanaButtonYU = new KanaButton( Kana.get( "yu" ) ); this.add( kanaButtonYU );
            kanaButtonRU = new KanaButton( Kana.get( "ru" ) ); this.add( kanaButtonRU );
            this.add( new Panel() );
            kanaButtonGU = new KanaButton( Kana.get( "gu" ) ); this.add( kanaButtonGU );
            kanaButtonZU = new KanaButton( Kana.get( "zu" ) ); this.add( kanaButtonZU );
            kanaButtonDU = new KanaButton( Kana.get( "zu*" ) ); this.add( kanaButtonDU );
            kanaButtonBU = new KanaButton( Kana.get( "bu" ) ); this.add( kanaButtonBU );
            kanaButtonPU = new KanaButton( Kana.get( "pu" ) ); this.add( kanaButtonPU );

            kanaButtonE = new KanaButton( Kana.get( "e" ) ); this.add( kanaButtonE );
            kanaButtonKE = new KanaButton( Kana.get( "ke" ) ); this.add( kanaButtonKE );
            kanaButtonSE = new KanaButton( Kana.get( "se" ) ); this.add( kanaButtonSE );
            kanaButtonTE = new KanaButton( Kana.get( "te" ) ); this.add( kanaButtonTE );
            kanaButtonNE = new KanaButton( Kana.get( "ne" ) ); this.add( kanaButtonNE );
            kanaButtonHE = new KanaButton( Kana.get( "he" ) ); this.add( kanaButtonHE );
            kanaButtonME = new KanaButton( Kana.get( "me" ) ); this.add( kanaButtonME );
            this.add( new Panel() );
            kanaButtonRE = new KanaButton( Kana.get( "re" ) ); this.add( kanaButtonRE );
            this.add( new Panel() ); //kanaButtonWE = new KanaButton( Kana.get( "we" ) ); this.add( kanaButtonWE );
            kanaButtonGE = new KanaButton( Kana.get( "ge" ) ); this.add( kanaButtonGE );
            kanaButtonZE = new KanaButton( Kana.get( "ze" ) ); this.add( kanaButtonZE );
            kanaButtonDE = new KanaButton( Kana.get( "de" ) ); this.add( kanaButtonDE );
            kanaButtonBE = new KanaButton( Kana.get( "be" ) ); this.add( kanaButtonBE );
            kanaButtonPE = new KanaButton( Kana.get( "pe" ) ); this.add( kanaButtonPE );

            kanaButtonO = new KanaButton( Kana.get( "o" ) ); this.add( kanaButtonO );
            kanaButtonKO = new KanaButton( Kana.get( "ko" ) ); this.add( kanaButtonKO );
            kanaButtonSO = new KanaButton( Kana.get( "so" ) ); this.add( kanaButtonSO );
            kanaButtonTO = new KanaButton( Kana.get( "to" ) ); this.add( kanaButtonTO );
            kanaButtonNO = new KanaButton( Kana.get( "no" ) ); this.add( kanaButtonNO );
            kanaButtonHO = new KanaButton( Kana.get( "ho" ) ); this.add( kanaButtonHO );
            kanaButtonMO = new KanaButton( Kana.get( "mo" ) ); this.add( kanaButtonMO );
            kanaButtonYO = new KanaButton( Kana.get( "yo" ) ); this.add( kanaButtonYO );
            kanaButtonRO = new KanaButton( Kana.get( "ro" ) ); this.add( kanaButtonRO );
            kanaButtonWO = new KanaButton( Kana.get( "o*" ) ); this.add( kanaButtonWO );
            kanaButtonGO = new KanaButton( Kana.get( "go" ) ); this.add( kanaButtonGO );
            kanaButtonZO = new KanaButton( Kana.get( "zo" ) ); this.add( kanaButtonZO );
            kanaButtonDO = new KanaButton( Kana.get( "do" ) ); this.add( kanaButtonDO );
            kanaButtonBO = new KanaButton( Kana.get( "bo" ) ); this.add( kanaButtonBO );
            kanaButtonPO = new KanaButton( Kana.get( "po" ) ); this.add( kanaButtonPO );

            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
            kanaButtonN = new KanaButton( Kana.get( "n" ) ); this.add( kanaButtonN );
            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
            this.add( new Panel() );
        }

        public void update() {
            Component[] comp = this.getComponents();
            for( int i = 0; i < comp.length; i++ )
                comp[ i ].repaint();
        }

        public void hightlightKana( Kana kana ) {
            //Find corresponding KanaButton
            Component[] comp = this.getComponents();
            int i = 0;
            for( ; i < comp.length; i++ ) {
                if( comp[ i ] instanceof KanaButton && ((KanaButton)comp[ i ]).kana.equals( kana ) )
                    break;
            }
            KanaButton kb = ((KanaButton)comp[ i ]);

            kb.highlight();
        }

        public void unhighlightKana() {
            Component[] comp = this.getComponents();
            KanaButton kb = null;
            int i = 0;
            while( i < comp.length && kb == null ) {
                if( comp[ i ] instanceof KanaButton && ((KanaButton)comp[ i ]).isHighlighted() )
                    kb = ((KanaButton)comp[ i ]);
                else
                    i++;
            }
            if( kb != null )
                kb.unhighlight();
        }

        public Insets getInsets() {
            return( new Insets( 5, 5, 5, 5 ) );
        }

        private Checkbox checkboxA;
        private Checkbox checkboxK;
        private Checkbox checkboxS;
        private Checkbox checkboxT;
        private Checkbox checkboxN;
        private Checkbox checkboxH;
        private Checkbox checkboxM;
        private Checkbox checkboxY;
        private Checkbox checkboxR;
        private Checkbox checkboxW;
        private Checkbox checkboxG;
        private Checkbox checkboxZ;
        private Checkbox checkboxD;
        private Checkbox checkboxB;
        private Checkbox checkboxP;

        private KanaButton kanaButtonA;
        private KanaButton kanaButtonBA;
        private KanaButton kanaButtonBE;
        private KanaButton kanaButtonBI;
        private KanaButton kanaButtonBO;
        private KanaButton kanaButtonBU;
        private KanaButton kanaButtonDA;
        private KanaButton kanaButtonDE;
        private KanaButton kanaButtonDI;
        private KanaButton kanaButtonDO;
        private KanaButton kanaButtonDU;
        private KanaButton kanaButtonE;
        private KanaButton kanaButtonGA;
        private KanaButton kanaButtonGE;
        private KanaButton kanaButtonGI;
        private KanaButton kanaButtonGO;
        private KanaButton kanaButtonGU;
        private KanaButton kanaButtonHA;
        private KanaButton kanaButtonHE;
        private KanaButton kanaButtonHI;
        private KanaButton kanaButtonHO;
        private KanaButton kanaButtonHU;
        private KanaButton kanaButtonI;
        private KanaButton kanaButtonKA;
        private KanaButton kanaButtonKE;
        private KanaButton kanaButtonKI;
        private KanaButton kanaButtonKO;
        private KanaButton kanaButtonKU;
        private KanaButton kanaButtonMA;
        private KanaButton kanaButtonME;
        private KanaButton kanaButtonMI;
        private KanaButton kanaButtonMO;
        private KanaButton kanaButtonMU;
        private KanaButton kanaButtonN;
        private KanaButton kanaButtonNA;
        private KanaButton kanaButtonNE;
        private KanaButton kanaButtonNI;
        private KanaButton kanaButtonNO;
        private KanaButton kanaButtonNU;
        private KanaButton kanaButtonO;
        private KanaButton kanaButtonPA;
        private KanaButton kanaButtonPE;
        private KanaButton kanaButtonPI;
        private KanaButton kanaButtonPO;
        private KanaButton kanaButtonPU;
        private KanaButton kanaButtonRA;
        private KanaButton kanaButtonRE;
        private KanaButton kanaButtonRI;
        private KanaButton kanaButtonRO;
        private KanaButton kanaButtonRU;
        private KanaButton kanaButtonSA;
        private KanaButton kanaButtonSE;
        private KanaButton kanaButtonSI;
        private KanaButton kanaButtonSO;
        private KanaButton kanaButtonSU;
        private KanaButton kanaButtonTA;
        private KanaButton kanaButtonTE;
        private KanaButton kanaButtonTI;
        private KanaButton kanaButtonTO;
        private KanaButton kanaButtonTU;
        private KanaButton kanaButtonU;
        private KanaButton kanaButtonWA;
        private KanaButton kanaButtonWE;
        private KanaButton kanaButtonWI;
        private KanaButton kanaButtonWO;
        private KanaButton kanaButtonYA;
        private KanaButton kanaButtonYO;
        private KanaButton kanaButtonYU;
        private KanaButton kanaButtonZA;
        private KanaButton kanaButtonZE;
        private KanaButton kanaButtonZI;
        private KanaButton kanaButtonZO;
        private KanaButton kanaButtonZU;
    }

    /**
     * Canvas on which asked kanas or romaji sounds are displayed.
     */

    class Screen extends Canvas {
        public Screen() {
            this.setFont( new Font( "Arial", Font.BOLD, 48 ) );
            this.setForeground( Color.black );
            fm = this.getFontMetrics( this.getFont() );
        }

        public void paint( Graphics g ) {
            super.paint( g );
            if( currKana != null ) {
                Dimension dim = this.getSize();
                int x, y, kanaWidth, kanaHeight;
                switch( testType ) {
                    case ROMAJI_KATAKANA :
                    case ROMAJI_HIRAGANA :
                        x = ( dim.width - fm.stringWidth( currKana.romaji ) ) / 2;
                        y = ( dim.height - fm.getHeight() ) / 2 + fm.getHeight() / 2;
                        g.setColor( Color.black );
                        g.drawString( currKana.romaji, x, y );
                        break;
                    case KATAKANA_ROMAJI :
                        kanaWidth = currKana.katakana.getWidth( this ) * 2;
                        kanaHeight = currKana.katakana.getHeight( this ) * 2;
                        x = ( dim.width - kanaWidth ) / 2;
                        y = ( dim.height - kanaHeight ) / 2;
                        g.drawImage( currKana.katakana, x, y, kanaWidth, kanaHeight, this );
                        break;
                    case HIRAGANA_ROMAJI :
                        kanaWidth = currKana.hiragana.getWidth( this ) * 2;
                        kanaHeight = currKana.hiragana.getHeight( this ) * 2;
                        x = ( dim.width - kanaWidth ) / 2;
                        y = ( dim.height - kanaHeight ) / 2;
                        g.drawImage( currKana.hiragana, x, y, kanaWidth, kanaHeight, this );
                        break;
                }
            }
        }

        private FontMetrics fm;
    }

    //KanaButton should extend Button but it doesn't work well on Netscape.
    class KanaButton extends Canvas /*Button*/ {
        public KanaButton( Kana kana ) {
            super();
            this.kana = kana;
            this.isHighlighted = false;
            this.setFont( new Font( "Arial", Font.BOLD, 24 ) );
            this.setBackground( Color.lightGray );
            this.setForeground( Color.black );
            fm = this.getFontMetrics( this.getFont() );
            //Should add an actionListener instead if KanaButton extends Button.
            this.addMouseListener( new KanaListener( this ) );
        }

        public void paint( Graphics g ) {
            Dimension dim = this.getSize();
            int x, y;
            switch( testType ) {
                case ROMAJI_KATAKANA :
                    x = ( dim.width - kana.katakana.getWidth( this ) ) / 2;
                    y = ( dim.height - kana.katakana.getHeight( this ) ) / 2;
                    g.drawImage( kana.katakana, x, y, this );
                    break;
                case ROMAJI_HIRAGANA :
                    x = ( dim.width - kana.katakana.getWidth( this ) ) / 2;
                    y = ( dim.height - kana.katakana.getHeight( this ) ) / 2;
                    g.drawImage( kana.hiragana, x, y, this );
                    break;
                case KATAKANA_ROMAJI :
                case HIRAGANA_ROMAJI :
                    x = ( dim.width - fm.stringWidth( kana.romaji ) ) / 2;
                    y = ( dim.height - fm.getHeight() ) / 2 + fm.getHeight() - 4;
                    g.setColor( Color.black );
                    g.drawString( kana.romaji, x, y );
                    break;
            }
            //Could be removed if KanaButton extends Button.
            g.setColor( Color.black );
            g.drawRect( 0, 0, this.getSize().width - 1, this.getSize().height - 1);
        }

        public void highlight() {
            isHighlighted = true;
            this.setBackground( Color.red );
            this.repaint(0);
            this.repaint();
        }

        public void unhighlight() {
            isHighlighted = false;
            this.setBackground( Color.lightGray );
            this.repaint(0);
            this.repaint();
        }

        public boolean isHighlighted() {
            return( isHighlighted );
        }

        public Kana     kana;

        private boolean     isHighlighted;
        private FontMetrics fm;
    }

    class Feedback extends Label {
        Feedback() {
            super( "", Label.LEFT );
        }
        
        public void setLocale( Locale locale ) {
            super.setLocale( locale );
            update();
        }

        void update() {
            switch( KanaSensei.this.state ) {
                case ASKING_KANA :
    	            this.setForeground( Color.black );
                    this.setText( resBundle.getString( "ClickSymbol" ) );
                    break;
                case RIGHT_KANA :
	                this.setForeground( Color.red );
	                this.setText( resBundle.getString( "ShowAnswer" ) );
                    break;
                case WRONG_KANA :
	                this.setForeground( Color.red );
	                this.setText( resBundle.getString( "WrongAnswer" ) );
                    break;
            }
        }
    }
    
    /**
     */

    static protected ResourceBundle resBundle = ResourceBundle.getBundle( 
        KanaSensei.class.getName() + "Ress", Locale.ENGLISH );

    //GUI
    private MenuBar             menubar;

    private Menu                menuOptions;
    private Menu                menuLanguage;
    private Menu                menuViews;
    private Menu                menuHelp;

    private CheckboxMenuItem    menuItemRomaKata;
    private CheckboxMenuItem    menuItemRomaHira;
    private CheckboxMenuItem    menuItemHira;
    private CheckboxMenuItem    menuItemKata;
    
    private CheckboxMenuItem    menuItemEnglish;
    private CheckboxMenuItem    menuItemFrench;
    private CheckboxMenuItem    menuItemSpanish;
    private CheckboxMenuItem    menuItemGerman;
    private CheckboxMenuItem    menuItemItalian;

    private CheckboxMenuItem    menuItemAllKanas;
    private CheckboxMenuItem    menuItemLoopTest;
    private CheckboxMenuItem    menuItemHasSound;

    private MenuItem            menuItemNewTest;

    private MenuItem            menuItemHelp;
    private MenuItem            menuItemAbout;
    private MenuItem            menuItemLicense;

    private Keyboard            keyboard;
    private Panel               panelOutput;
    private Screen              screen;
    private Panel               panelConsole;
    private ScoreBoard          scoreBoard;
    private Feedback            feedback;


    //Data members
    protected static final int  ASKING_KANA = 0;
    protected static final int  RIGHT_KANA = 1; 
    protected static final int  WRONG_KANA = 2;

    protected static final int    ROMAJI_KATAKANA     = 0;
    protected static final int    ROMAJI_HIRAGANA     = 1;
    protected static final int    HIRAGANA_ROMAJI     = 2;
    protected static final int    KATAKANA_ROMAJI     = 3;

    protected int testType;

    protected int state;
    protected int attempts;
    protected int rightAnswers;
    protected int wrongAnswers;

    protected boolean isAllKanas;
    protected boolean isLoopTest;
    protected boolean hasSound;

    protected Toggle kanaSetA;
    protected Toggle kanaSetK;
    protected Toggle kanaSetS;
    protected Toggle kanaSetT;
    protected Toggle kanaSetN;
    protected Toggle kanaSetH;
    protected Toggle kanaSetM;
    protected Toggle kanaSetY;
    protected Toggle kanaSetR;
    protected Toggle kanaSetW;
    protected Toggle kanaSetG;
    protected Toggle kanaSetZ;
    protected Toggle kanaSetD;
    protected Toggle kanaSetB;
    protected Toggle kanaSetP;

    protected Kana currKana;

    private Applet           applet;

    private static final int        MAX_ATTEMPTS        = 2;

    private Vector kanaPool;

    public static void main( String[] args ) {
        KanaSensei sensei = new KanaSensei( null );
        sensei.setLocale( Locale.ENGLISH );
        sensei.setVisible( true );
    }
}

