package com.eng.framework.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 *
 * <pre>
 * Title: 환경설정 관리
 * Description: 오라클에 올릴경우를 대비해 loose하게 코딩한다.(commons-configuration-1.2.jar)
 * Copyright: Copyright (c) 2006
 * Company: www.UbiwareLab.com
 * @author yunkidon@hotmail.com
 * @version 1.0
 * </pre>
 */
public class Config extends PropertiesConfiguration {

    /**
     * 기본 생성자
     */
    protected Config(String propertiesName) throws Exception {
        super(propertiesName);
        super.setReloadingStrategy(new FileChangedReloadingStrategy());
        super.setAutoSave(true);
    }

    /**
     *
     * @param propertiesName String
     * @param editAble boolean
     * @throws Exception
     */
    protected Config(String propertiesName, boolean editAble) throws Exception {
        super(propertiesName);
        super.setAutoSave(editAble);
    }
}
