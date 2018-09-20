package com.ais.patient.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

public class MobileUtil {
    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     *
     * @param input
     * @return
     */
    public static boolean containSpace(CharSequence input){
        return Pattern.compile("\\s+").matcher(input).find();
    }


    /**
     * 禁止EditText输入空格
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if(" ".equals(source) || "\n".equals(source)){
                    //空格和换行都转换为""
                    return "";
                }else{
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText){

        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find())return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 只显示手机号头和尾几位数
     * @param textView
     * @param phoneNum
     * @param hintBegin  开始的显示为* 的位置
     * @param hindEnd    结束显示为* 的位置
     */
    public static void setTextHintMiddle(TextView textView, String phoneNum, int hintBegin, int hindEnd){
        if(!TextUtils.isEmpty(phoneNum) && phoneNum.length() >= hindEnd ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < phoneNum.length(); i++) {
                char c = phoneNum.charAt(i);
                if (hintBegin>0){
                    if (i >= hintBegin-1 && i < hindEnd) {
                        sb.append('*');
                    } else {
                        sb.append(c);
                    }
                }

            }

            textView.setText(sb.toString());
        }

    }
}
