package com.eng.framework.tray;


import com.eng.framework.log.Log;
/**
 * Tray에 담겨진 값에 대해 다양한 연산을 수행하는 유틸리티 클래스.
 *
 * @author 진헌규
 * @version 1.0, 2004-04-19
 */
public class TrayUtil {
    /**
     * 생성을 막기 위한 private 생성자.
     */
    private TrayUtil() {
    }

    /**
     * 트레이에 담긴 값을 다른 트레이에 합친다.
     * target tray에 이미 동일한 key가 있었을 경우에는 뒤에 append된다.
     *
     * @param target    복사의 target이 되는 tray
     * @param src       복사의 source가 되는 tray
     */
    public static void mergeTray(Tray target, Tray src) {
        mergeTray(target, src, false);
    }

    /**
     * 트레이에 담긴 값을 다른 트레이에 합친다.
     * target tray에 이미 동일한 key가 있었을 경우에는 뒤에 append된다.
     *
     * @param target    복사의 target이 되는 tray
     * @param src       복사의 source가 되는 tray
     * @param keyToLower    key를 소문자로 변환
     */
    public static void mergeTray(Tray target, Tray src, boolean keyToLower) {
        String[] src_keys = src.getKeys();

        for (int i = 0; i < src_keys.length; i++) {
            int size = src.size(src_keys[i]);
            for (int j = 0; j < size; j++) {
                target.add(keyToLower ? src_keys[i].toLowerCase() : src_keys[i], src.get(src_keys[i], j));
            }
        }
    }
    
    public static Tray copyTray(Tray src, int index){
    	if (src == null) return null;
    	if (src.getRowCount() == 0 ) return null;
    	if (src.getRowCount() < index ) return null;
    	
    	Tray retTray = new NewTray();
    	for (int i=0; i<src.getKeys().length; i++){
    		retTray.setString(src.getKeys()[i], src.getString(src.getKeys()[i], index));
    	}
    	
    	return retTray;
    }
    
    public static Tray copyTray(Tray src){
    	if (src == null) return null;
    	
    	Tray retTray = new NewTray();
    	for (int i=0; i<src.getKeys().length; i++){
    		retTray.setString(src.getKeys()[i], src.getString(src.getKeys()[i]));
    	}
    	
    	return retTray;
    }
}
