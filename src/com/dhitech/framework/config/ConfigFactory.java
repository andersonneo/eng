package com.dhitech.framework.config;

import java.util.Hashtable;

import com.dhitech.framework.exception.PropNotFoundException;

/**
 *
 * <pre>
 * Title: 환경설정 ConfigFactroy를 통해 Config를 접근한다.
 * Description: 오라클에 올릴경우를 대비해 loose하게 코딩한다.(commons-configuration-1.2.jar)
 * Copyright: Copyright (c) 2006
 * Company: www.UbiwareLab.com
 * @author yunkidon@hotmail.com
 * @version 1.0
 * </pre>
 */


/**
 * 환경 정보,메시지등 정적인 정보를 Key에 의해 쉽게 접근 할 수 있는 interface를 제공한다.
 <br>
 <pre>
    try{
        ConfigFactory cf = ConfigFactory.getInstance();
        Config config = cf.getConfiguration("base.properties");
        System.out.print(config.getString("ldap.server.primary.host"));
        ConfigFactory cf1 = ConfigFactory.getInstance();
        Config config1 = cf.getConfiguration("sso.properties");
        System.out.print("\n"+config1.getString("ldap.schema.root"));
    }catch(PropertiesNotFoundException e){

    }
 </pre>
 */

public class ConfigFactory {

    private static Hashtable configurationGroup;
    private static ConfigFactory theInstance;

    /**
     * 기본 생성자
     */
    private ConfigFactory() {
        configurationGroup = new Hashtable();
    }

    /**
     * Double Check Lock 구현
     * @return <code>com.ubiware.framework.config.ConfigFactory</code> - 프로퍼티 공장
     */
    public static ConfigFactory getInstance() {
        if (theInstance == null) {
            synchronized (ConfigFactory.class) {
                if (theInstance == null) {
                    theInstance = new ConfigFactory();
                }
            }
        }
        return theInstance;
    }


    /**
     * 프로퍼티 접근
     * @param <code>String</code> 요청 프로퍼티의 풀이름(base.properties)
     * @return <code>com.ubiware.framework.config.Config</code> - 요청 Config
     * @throws <code>com.ubiware.framework.exception.PropertiesNotFoundException</code> - 파일읽기 실패
     */
    public Config getConfiguration(String prop_full_name) throws PropNotFoundException {
        try {
            if (!configurationGroup.containsKey(prop_full_name)) {
                configurationGroup.put(prop_full_name, new Config(prop_full_name));
            }
        } catch (Exception ex) {
            System.err.print(ex);
            throw new PropNotFoundException("properties 읽기 실패");
        }

        return (Config) configurationGroup.get(prop_full_name);
    }
}
