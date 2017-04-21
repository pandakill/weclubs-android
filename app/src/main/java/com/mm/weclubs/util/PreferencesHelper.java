package com.mm.weclubs.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import com.mm.weclubs.util.DecodeHelper.EncodeType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.prefs.Preferences;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/4 下午2:57
 * 描述: preferences的工具类
 */
public class PreferencesHelper {

    private static final String TAG = PreferencesHelper.class.getSimpleName();
    private static final String PACKAGE_NAME = Preferences.class.getPackage().getName();

    private static PreferencesHelper sInstance = null;
    private static Context sContext;
    private static SharedPreferences sSharedPreferences;

    public static PreferencesHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesHelper();
        }
        sContext = context;
        if (sSharedPreferences == null) {
            sSharedPreferences = sContext.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        }

        return sInstance;
    }

    /**
     * 判断是否存在该键值对
     *
     * @param key   键
     * @return  true:存在; false:不存在
     */
    public boolean contains(String key) {
        return sSharedPreferences.contains(key);
    }

    /**
     * 保存字符串类型
     *
     * @param key   键
     * @param value 值
     * @param type  加密类型
     */
    public void put(String key, String value, EncodeType type) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        if (!LibCommonUtils.isEmpty(value) && type != null) {
            value = DecodeHelper.getInstance().encode(value.getBytes(), type);
        }
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 保存整形类型
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, int value) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 保存bool类型
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, boolean value) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 保存序列化实体{@link Serializable} 或者 {@link Parcelable}
     *
     * @param key   键
     * @param str   实体对象
     */
    public void put(String key, String str) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(key, str);
        editor.apply();
    }

    /**
     * 保存序列化实体{@link Serializable} 或者 {@link Parcelable}
     *
     * @param key   键
     * @param obj   实体对象
     * @param type  加密类型,可以为空,如果为空则不加密
     */
    public void put(String key, Object obj, EncodeType type) {
        if (obj == null) {
            put(key, "");
            return;
        }
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);

            String value;
            // 如果加密类型不为空的话,需要进行加密处理
            if (type == null) {
                value = new String(baos.toByteArray());
            } else {
                value = DecodeHelper.getInstance().encode(baos.toByteArray(), type);
            }

            put(key, value);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取字符串类型
     *
     * @param key   键
     * @param defStr 缺省值
     * @return 返回获取到的值,如果发生异常或者为空的时候返回缺省值
     */
    public String getAsString(String key, String defStr) {
        return sSharedPreferences.getString(key, defStr);
    }

    /**
     * 获取整形类型的缓存
     *
     * @param key   键
     * @param defInt    缺省值
     * @return 返回获取到的值
     */
    public int getAsInteger(String key, int defInt) {
        return sSharedPreferences.getInt(key, defInt);
    }

    /**
     * 获取bool类型的值
     *
     * @param key   键
     * @param def   值
     * @return  返回获取到的值
     */
    public boolean getAsBool(String key, boolean def) {
        return sSharedPreferences.getBoolean(key, def);
    }

    /**
     * 获取序列化实体 {@link Parcelable} 或者 {@link Serializable}
     *
     * @param key   键
     * @param encodeType    解密类型,如果不需要解密则传{@code null}
     * @return  返回获取到的对象
     */
    public Object getAsObject(String key, EncodeType encodeType) {
        String baseStr = getAsString(key, "");
        Object resultObj = null;
        byte[] baseByte = baseStr.getBytes();
        if (!LibCommonUtils.isEmpty(baseStr)) {

            // 如果需要解密的话,需要调用方法进行解密
            if (encodeType != null) {
                baseByte = DecodeHelper.getInstance().decode(baseStr, encodeType);
            }

            ByteArrayInputStream baos = new ByteArrayInputStream(baseByte);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(baos);
                resultObj = ois.readObject();

            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ois != null) {
                        ois.close();
                    }
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultObj;
    }

}
