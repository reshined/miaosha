package com.seckill.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 作者 wxh:
 * @version 创建时间：2017年5月26日 下午4:03:10 字符串拆分
 */
public class TextUtils {
	/**
	 * 处理输入的字符串，将字符串分割成以byteLength为宽度的多行字符串。
	 * 根据需要，英文字符的空格长度为0.5，汉字的长度为2（GBK编码下，UTF-8下为3），数字英文字母宽度为1.05。
	 * 
	 * @param inputString
	 *            输入字符串
	 * @param byteLength
	 *            以byteLength的长度进行分割（一行显示多宽）
	 * @return 处理过的字符串
	 */
	public static String[] getChangedString(String inputString, int byteLength) {
		String outputString = inputString;
		String[] strs = null;
		try {

			char[] chars = inputString.toCharArray();
			char[] workChars = new char[chars.length * 2];

			// i为工作数组的角标，length为工作过程中长度,stringLength为字符实际长度,j为输入字符角标
			int i = 0, stringLength = 0;
			float length = 0;
			for (int j = 0; j < chars.length; i++, j++) {

				// 如果源字符串中有换行符，此处要将工作过程中计算的长度清零
				if (chars[j] == '\n') {
					length = 0;
				}
				try {
					workChars[i] = chars[j];
					// 对汉字字符进行处理
					if (new Character(chars[j]).toString().getBytes(
							"GBK").length == 2 /*
												 * && chars[j] != '”' &&
												 * chars[j] != '“'
												 */) {
						length++;
						if (length >= byteLength) {
							if (chars[j + 1] != '\n') {
								i++;
								stringLength++;
								workChars[i] = '\n';
							}
							length = 0;
						}
					} else if (new Character(chars[j]).toString().getBytes("GBK").length == 1) {
						// 对空格何应为字符和数字进行处理。
						/*
						 * if (chars[j] == ' ' ) { length -= 0.5; }else { length
						 * += 0.5; }
						 */
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				stringLength++;
				length++;
				// 长度超过给定的长度，插入\n
				if (length >= byteLength) {
					if (chars[j + 1] != '\n') {
						i++;
						stringLength++;
						workChars[i] = '\n';
					}
					length = 0;
				}
			}
			outputString = new String(workChars).substring(0,
					stringLength)/* .trim() */;
			// System.out.println(outputString);
			strs = outputString.split("\n");
			// System.out.println(strs.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}

	/**
	 * 按照指定长度切割英文字符串,英文按照元音切割(不一定完全整齐)
	 * 
	 * @param str
	 * @param lengthOfRow
	 * @return
	 */

	public static String splitStringByVowel(String engString, int lengthOfRow) {
		String stri = engString;
		// 添加换行符后的字符串
		String lineBreakStr = ""; // 计算字符串的总长度
		int len = stri.length();
		// 空格
		String space = " ";
		// 已计算的字符串的长度
		int lengthOfCalcuted = 0; //
		boolean bl = true;
		while (bl) {
			// 当字符串只有一行的时候
			if (len / lengthOfRow == 0) {
				bl = false;
				lineBreakStr = engString;
				return lineBreakStr;
			} else {

				if (lengthOfCalcuted + lengthOfRow < len) { // 下一行判断前的字符串
					String sub = stri.substring(lengthOfCalcuted + lengthOfRow - 1, lengthOfCalcuted + lengthOfRow);

					if (sub.compareTo(space) == 0) {
						lineBreakStr += stri.substring(lengthOfCalcuted, lengthOfCalcuted + lengthOfRow);
						lineBreakStr += "\n";
						lengthOfCalcuted += lengthOfRow;

					} else {
						int lenOfRow = lengthOfRow - 1;

						for (int i = lenOfRow; i > 0; i--) {
							String subF = stri.substring(lengthOfCalcuted + i - 1, lengthOfCalcuted + i);
							if (subF.compareTo(space) == 0) {
								lineBreakStr += stri.substring(lengthOfCalcuted, lengthOfCalcuted + i) + "\n";
								lengthOfCalcuted += i;

								break;
							} else if (subF.compareTo("a") == 0 || subF.compareTo("o") == 0 || subF.compareTo("e") == 0
									|| subF.compareTo("i") == 0 || subF.compareTo("u") == 0) {
								lineBreakStr += stri.substring(lengthOfCalcuted, lengthOfCalcuted + i) + "-" + "\n";
								lengthOfCalcuted += i;

								break;

							}
							if (i == 1) {

								lineBreakStr += stri.substring(lengthOfCalcuted, lengthOfCalcuted + i);
								lineBreakStr += "\n";
								lengthOfCalcuted += 1;
								break;
							}
						}
					}

				} else {
					String sub = stri.substring(lengthOfCalcuted, len);
					bl = false;
					lineBreakStr += sub;
					return lineBreakStr;
				}

			}
		}
		return lineBreakStr;

	}

	/**
	 * 字符串按照指定长度拆分 不拆分单词
	 * 
	 * @param str
	 * @param lengthOfRow
	 * @return
	 */
	public static String splitString(String str, int lengthOfRow) {
		String stri = str;

		// 添加换行符后的字符串
		String lineBreakStr = "";
		// 计算字符串的总长度
		int len = stri.length();

		// 空格
		String space = " ";
		// 已计算的字符串的长度
		int lengthOfCalcuted = 0;
		// 判断是否循环
		boolean bl = true;
		while (bl) {
			// 当字符串只有一行的时候
			if (len / lengthOfRow == 0) {
				bl = false;
				lineBreakStr = str;
				return lineBreakStr;
			} else {
				if (lengthOfCalcuted + lengthOfRow < len) {
					// 下一行判断前的字符串
					String sub = stri.substring(lengthOfCalcuted + lengthOfRow - 1, lengthOfCalcuted + lengthOfRow);
					String point = ".";
					System.out.println(point.compareTo("."));
					if (sub.compareTo(space) == 0 || sub.compareTo(".") == 0 || sub.compareTo(",") == 0) {
						lineBreakStr += stri.substring(lengthOfCalcuted, lengthOfCalcuted + lengthOfRow);
						lineBreakStr += "\n";
						lengthOfCalcuted += lengthOfRow;

					} else {
						int lenOfRow = lengthOfRow - 1;
						// 前面到空格的字节数
						int numFront = 0;
						// 后面到空格的字节数
						int numOfBehind = 0;
						int lenOfBehind = lengthOfRow + 1;
						for (int i = lenOfRow; i > 0; i--) {
							String subF = stri.substring(lengthOfCalcuted + i - 1, lengthOfCalcuted + i);
							numFront++;
							if (subF.compareTo(space) == 0 || subF.compareTo(".") == 0 || subF.compareTo(",") == 0) {
								// lineBreakStr+=stri.substring(lengthOfCalcuted,lengthOfCalcuted+i)+"\n";
								// lengthOfCalcuted+=i;
								// System.out.println(subF);

								break;
							} else {
								System.out.println("qian" + subF);
							}
							if (i == 1) {

								// lineBreakStr+=stri.substring(lengthOfCalcuted,lengthOfCalcuted+i);
								// lineBreakStr+="\n";
								// lengthOfCalcuted+=1;
								break;
							}

						}
						for (int i = lenOfBehind;; i++) {
							if (lengthOfCalcuted + i == len) {
								lineBreakStr += stri.substring(lengthOfCalcuted,
										lengthOfCalcuted + lengthOfRow + numOfBehind - 1) + "\n";
								return lineBreakStr;

							}
							String subFw = stri.substring(lengthOfCalcuted + i - 1, lengthOfCalcuted + i);
							numOfBehind++;
							if (subFw.compareTo(space) == 0 || subFw.compareTo(".") == 0 || subFw.compareTo(",") == 0) {

								break;
							} else {
								System.out.println("hou" + subFw);
							}
						}

						if (numOfBehind > numFront) {

							lineBreakStr += stri.substring(lengthOfCalcuted, lengthOfCalcuted + lengthOfRow - numFront)
									+ "\n";
							lengthOfCalcuted = lengthOfCalcuted + lengthOfRow - numFront;
						} else {
							lineBreakStr += stri.substring(lengthOfCalcuted,
									lengthOfCalcuted + lengthOfRow + numOfBehind) + "\n";
							lengthOfCalcuted = lengthOfCalcuted + lengthOfRow + numOfBehind;
						}
					}

				} else {
					String sub = stri.substring(lengthOfCalcuted, len);
					bl = false;
					lineBreakStr += sub;
					return lineBreakStr;
				}

			}
		}
		return lineBreakStr;
	}

	/**
	 * 按照指定长度整齐切割字符串 单词可能拆分
	 * 
	 * @param inputString
	 *            需要切割的源字符串
	 * @param length
	 *            指定的长度
	 * @return 拆后的的字符串集合
	 */
	public static List<String> getDivLines(String inputString, int length) {
		List<String> divList = new ArrayList<String>();
		int remainder = (inputString.length()) % length;
		// 一共要分割成几段
		int number = (int) Math.floor((inputString.length()) / length);
		for (int index = 0; index < number; index++) {
			String childStr = inputString.substring(index * length, (index + 1) * length);
			//
			divList.add(childStr);
		}
		if (remainder > 0) {
			String cStr = inputString.substring(number * length, inputString.length());
			divList.add(cStr);
		}
		return divList;
	}

	/**
	 * 按照指定长度整齐切割英文字符串 单词拆分后加链接符
	 * 
	 * @param inputString
	 *            需要切割的源字符串
	 * @param length
	 *            指定的长度
	 * @return 拆后的的字符串集合
	 */
	@SuppressWarnings("unused")
	public static String getDivLinesPlus(String inputString, int length) {
		String str = inputString;
		// 字符串长度为空
		String cutString = "";// 裁剪出来的字符串
		String rowEndString = "";// 每行末尾字符
		String rowEndNextString = "";// 每行末尾字符下一位
		if (str.length() == 0 || str == null) {
			return null;
		} else {
			// 字符串长度不为空
			boolean flag = true;
			while (true) {
				if (str.length() <= length) {
					cutString += str.substring(0, str.length());
					flag = false;
					break;
				} else {
					rowEndString = String.valueOf(str.charAt(length - 1));
					rowEndNextString = String.valueOf(str.charAt(length));
					if (rowEndString.compareTo(",") == 0 || rowEndString.compareTo(".") == 0
							|| rowEndString.compareTo("\"") == 0 || rowEndString.compareTo(" ") == 0
							|| rowEndString.compareTo("?") == 0||rowEndString.compareTo("?") == 0) {
						cutString += str.substring(0, length) + "\n";
						str = str.substring(length, str.length());
					} else {
						if (rowEndNextString.compareTo(",") == 0 || rowEndNextString.compareTo(".") == 0
								|| rowEndNextString.compareTo("\"") == 0 || rowEndNextString.compareTo(" ") == 0
								|| rowEndNextString.compareTo("?") == 0||rowEndNextString.compareTo("?") == 0) {
							cutString += str.substring(0, length + 1) + "\n";
							str = str.substring(length + 1, str.length());
						} else {
							cutString += str.substring(0, length)+"-"+"\n";
							str = str.substring(length, str.length());
						}
					}
				}
			}
		}
		return cutString;
	}
	
	/**
	 * 获取指定字符串出现的次数
	 * 
	 * @param srcText 源字符串
	 * @param findText 要查找的字符串
	 * @return
	 */
	public static int appearNumber(String srcText, String findText) {
	    int count = 0;
	    Pattern p = Pattern.compile(findText);
	    Matcher m = p.matcher(srcText);
	    while (m.find()) {
	        count++;
	    }
	    return count;
	}
	
	public static void main(String[] args) {
//		ImageConfig imageConfig1 = new ImageConfig("万贤洪", "宋体", Font.BOLD | Font.PLAIN, 44, Color.BLACK, 760, 1090,
//				0.9f);
//		ImageConfig imageConfig2 = new ImageConfig("2017.5.10", "宋体", Font.BOLD | Font.PLAIN, 40, Color.BLACK, 250,
//				1095, 0.9f);
//		ImageConfig imageConfig3 = new ImageConfig("R" + "46546546327987", "宋体", Font.BOLD | Font.PLAIN, 24,
//				Color.BLACK, 600, 1387, 0.8f);
//		List<ImageConfig> imageConfigs = new ArrayList<ImageConfig>();
//		imageConfigs.add(imageConfig1);
//		imageConfigs.add(imageConfig2);
//		imageConfigs.add(imageConfig3);
//		ImageUtils.pressMoreText("D:/1.jpg", "D:/zz.jpg", imageConfigs);// 添加多出水印文字
//		ImageUtils.pressImage("D:/zz.jpg", "D:/zhang.png", "D:/zz.jpg", 700, 1040, 1f);// 添加图片水印
//
//		// 中文文字水印
//		String zh = "$，热爱公益、奉献爱心，在“$”项目中慷慨捐赠人民币￥$元，弘扬了扶危济困、助人为乐的传统美德和人道、博爱、奉献的红十字精神。为感谢您对红十字人道救助事业的支持，特授此证纪念，并表谢忱!";
//		// 替换$
//		zh = zh.replaceFirst("\\$", "万贤洪").replaceFirst("\\$", "看得见的希望").replaceFirst("\\$", String.valueOf(10000));
//		String[] zhStrs = TextUtils.getChangedString(zh, 56);
//		StringBuffer sb = new StringBuffer();
//		for(String s:zhStrs){
//			sb.append(s+"\n");
//		}
//		System.out.println(sb.toString());
//		int y = 670;
//		List<ImageConfig> zhImageConfigs = new ArrayList<ImageConfig>();
//		for (int i = 0; i < zhStrs.length; i++) {
//			ImageConfig imageConfig = new ImageConfig(zhStrs[i], "宋体", Font.PLAIN | Font.PLAIN, 28, Color.BLACK, 210, y,
//					1f);
//			zhImageConfigs.add(imageConfig);
//			y += 40;
//		}
//		ImageUtils.pressMoreText("D:/zz.jpg", "D:/zz.jpg", zhImageConfigs);
//
//		// 英文文字水印
//		String en = "    $,love public welfare,showered love lavishly,generous donation of ￥$ RMB in the project \"$\",carry forward the traditional virtues of helping the lives of the poor,and humanitarian,fraternity and dedication of the spirit.Thanks for your support of the Red Cross humanitarian relief work,the special delegate this card and your memory.";
//		// 转换拼音,首字母大写 ,替换$
//
//		en = en.replaceFirst("\\$", ChinToEngUtils.toUpperCaseFirstOne(ChinToEngUtils.getPingYin("万贤洪")))
//				.replaceFirst("\\$", String.valueOf(10000))
//				.replaceFirst("\\$", ChinToEngUtils.toUpperCaseFirstOne(ChinToEngUtils.getPingYin("看得见的额希望")));
//		List<String> enStrs = getDivLines(en, 56);
//		List<ImageConfig> enImageConfigs = new ArrayList<ImageConfig>();
//		for (int i = 0; i < enStrs.size(); i++) {
//			ImageConfig imageConfig = new ImageConfig(enStrs.get(i), "宋体", Font.PLAIN | Font.PLAIN, 28, Color.BLACK,
//					210, y, 1f);
//			enImageConfigs.add(imageConfig);
//			y += 30;
//		}
//		ImageUtils.pressMoreText("D:/zz.jpg", "D:/zz.jpg", enImageConfigs);
//		System.out.println(getDivLinesPlus(en, 56));
		
		
//		String srcText = "Hello World";
//	    String findText = "l";
//	    int num = appearNumber(zh, "\\$");
//	    System.out.println(num);
		String s ="张三三第三方个第三方个地方水电费固定死风格";
		s=s.replaceAll("*", "*");//姓名屏蔽
//		s=s.replaceAll("(\\w{3})(\\w+)(\\w{3})", "$1****$3");//身份证
//		s.replaceAll("([^x00-xff]{3})[^x00-xff]{4}([^x00-xff]{4})", "$1****$2");
		
		System.out.println(s);
	}
}
