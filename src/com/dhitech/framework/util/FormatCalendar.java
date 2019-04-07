package com.dhitech.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * <code>FormatCalendar</code>��<code>GregorianCalendar</code>瑜��곸냽諛쏄퀬 �대��곸쑝濡�
 * <code>SimpleDateFormat</code>���꾩엫諛쏆� �대옒�ㅼ엯�덈떎.
  * 
 * ���대옒�ㅻ� �ъ슜�섎㈃ Calendar瑜�String�쇰줈 蹂�솚�섍린 �꾪빐 蹂꾨룄濡�<code>SimpleDateFormat</code>���좎뼵��
 * �꾩슂媛��놁뒿�덈떎. 
 *
 * <strong>�ъ슜�덉젣</strong>
 * <blockquote>
 * <pre>
 *  FormatCalendar cal = new FormatCalendar(yyyy-MM-dd HH:mm:ss);
 *  System.out.println(cal.format());
  * </pre>
 * </blockquote>
 * 
 * @version 2004-04-07
 * @author 怨쎈룞��
 * @see java.util.GregorianCalendar
 * @see java.text.SimpleDateFormat 
 */

public class FormatCalendar extends GregorianCalendar {
	
    protected SimpleDateFormat simpleDateFormat = null;

    public FormatCalendar() {
        super();
        simpleDateFormat = new SimpleDateFormat();
    }

    public FormatCalendar(int year, int month, int date) {
        super(year, month, date);
        simpleDateFormat = new SimpleDateFormat();
    }

    public FormatCalendar(int year, int month, int date, int hour, int minute) {
        super(year, month, date, hour, minute);
        simpleDateFormat = new SimpleDateFormat();
    }

    public FormatCalendar(int year, int month, int date, int hour, int minute, int second) {
        super(year, month, date, hour, minute, second);
        simpleDateFormat = new SimpleDateFormat();
    }

    public FormatCalendar(Locale aLocale) {
        super(aLocale);
        simpleDateFormat = new SimpleDateFormat();
    }

    public FormatCalendar(TimeZone zone) {
        super(zone);
        simpleDateFormat = new SimpleDateFormat();
    }

    public FormatCalendar(TimeZone zone, Locale aLocale) {
        super(zone, aLocale);
        simpleDateFormat = new SimpleDateFormat();
    }

    public FormatCalendar(String pattern) {
        super();
        simpleDateFormat = new SimpleDateFormat(pattern);
    }

    public FormatCalendar(SimpleDateFormat simpleDateFormat) {
        super();
        this.simpleDateFormat = simpleDateFormat;
    }

    public FormatCalendar(String pattern, String text) throws ParseException {
        super();
        simpleDateFormat = new SimpleDateFormat(pattern);
        parse(text);
    }
    
    public void applyPattern(String pattern) {
        simpleDateFormat.applyPattern(pattern);
    }

    public String format() {
        return simpleDateFormat.format(getTime());
    }

    public String format(String pattern) {
        String result = null;

        String oldPattern = toPattern();
        applyPattern(pattern);
        result = format();
        applyPattern(oldPattern);

        return result;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void parse(String text) throws java.text.ParseException {
        setTime(simpleDateFormat.parse(text));
    }

    public void parse(String pattern, String text) throws java.text.ParseException {
        String oldPattern = toPattern();
        applyPattern(pattern);
        parse(text);
        applyPattern(oldPattern);
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public String toPattern() {
        return simpleDateFormat.toPattern();
    }
}