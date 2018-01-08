package org.pussinboots.morning.os.common.util;

import java.security.AlgorithmParameters;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.sf.json.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;


/**
 * 项目名称：xm-web-admin-cms
 * 类名称：WXAppletUserInfo 
 * 类描述：微信小程序解密
 * 创建人：张国浩 
 * 创建时间：日期：2018年1月4日 时间：下午12:39:10   
 */

public class WXAppletUserInfo {
	private static String AppletAPPID="wx7d718dc2ebe362ed";
	private static String AppletAppSecret="45bf5814569cbedcb9adc10d3ebf5ce3";
	/* public static void main(String[] args) {
		  WXAppletUserInfo wx=new WXAppletUserInfo();
		System.out.println(  wx.getSessionKeyAndOropenid("0714xjP11V5yON1fm9O11LGaP114xjP3"));
//		  wx.getUserInfo("RVssSenEJNGL0mixfUJmWlg+b2EsMUXVJcsu3dJ/5BzP1PywtXNdgq00Z0ZL64Pkm8WpDIrLZ/jhdkcFaib35/yjOlaPCBTSFomssAYr/piHiXg1UnoyzSLuHiwQSLCiPwz3Tk+La2j7AGtfXd6OJxTweNqQtN8kRULFoxEfjswRAgDfI+0wZImPevr/F5lQ9nbHCIbdlIOpR9uMz2/x3d0Y05DYr6FBbtDv6KgjQaduc4dDgRHmwgEYBp0jtbu+/W1xUkSav3RPaMvtQUv40cSzJYUR9kO4J2ZX+oPj87bPEtf/5lf3/0Y11s0hPjuLDDZz1vAZM/0vVCXfx6cq0HIMkBglZ5Y6kaA+l2nOWvWK00CrmClmJIKslBpxAwmbLfGX2oKAZRrL+wBDz06xIskdfcnpOe8fYceVMXFs/YapXvPINNPv6sc4OdfnQpm23fQto8AJ7SB6+G9Ug+U2cQ==","", "TYHiv03g4mptqzaWhaxBzA==");
		}*/


    /**
     * 获取微信小程序 session_key 和 openid
     */
    public static JSONObject getSessionKeyAndOropenid(String code){
        //微信端登录code值
        String wxCode = code;
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String,String> requestUrlParam = new HashMap<String,String>();
        requestUrlParam.put("appid", AppletAPPID);
        requestUrlParam.put("secret",AppletAppSecret);
        requestUrlParam.put("js_code", wxCode);
        requestUrlParam.put("grant_type", "authorization_code");
        JSONObject jsonObject = HttpsClientUtil.getInstance().sendGetRequest(requestUrl, requestUrlParam);
        return jsonObject;
    }
    /**
     * 获取微信小程序 获取access_token
     */
    public static JSONObject getAccessToken(){
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,String> requestUrlParam = new HashMap<String,String>();
        requestUrlParam.put("appid", AppletAPPID);
        requestUrlParam.put("secret",AppletAppSecret);
        requestUrlParam.put("grant_type", "client_credential");
        JSONObject jsonObject = HttpsClientUtil.getInstance().sendGetRequest(requestUrl, requestUrlParam);
        return jsonObject;
    }
    /**
     * 解密用户敏感数据获取用户信息
     * @param sessionKey 数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     */
    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.fromObject(result);
            }
        }catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
}



