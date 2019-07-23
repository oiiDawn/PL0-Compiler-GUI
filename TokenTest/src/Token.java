import javax.swing.JOptionPane;

public class Token {
	
	public static final int KEY_NUM = 16;	//关键字个数
	public static final int NUM_MAX = 14;	//number最大位数
	public static final int KEY_ALL = 34;
	public static final int LEN_MAX = 10;	//符号的最大位数
	public static final int PTR_MAX = 200;	//最多的虚拟机代码数
	
	public enum symbol {
		SYM_NUL, 
		
		SYM_BEGIN, SYM_CALL, SYM_CONST, SYM_END, SYM_IF, SYM_OOD, 
		SYM_PROCEDURE, SYM_THEN, SYM_VAR, SYM_WHILE, SYM_READ, SYM_WRITE,
		SYM_REPEAT, SYM_UNTIL, SYM_DO, SYM_ELSE, 
		
		SYM_IDENTIFIER, SYM_NUMBER,
		
		SYM_PLUS, SYM_MINUS, SYM_TIMES, SYM_SLASH,
		SYM_EQU,
		SYM_NEQ, 	//#
		SYM_LES, 	/* < */		SYM_LEQ, 		/* <= */
		SYM_GTR, 	/* > */		SYM_GEQ, 		/* >= */
		SYM_LPAREN, /* ( */		SYM_RPAREN, 	/* ) */
		SYM_COMMA,	/* , */		SYM_SEMICOLON, 	/* ; */
		SYM_PERIOD,	/* . */		SYM_BECOMES,	/* := */
		
	};
	
	public static char ch;						//getChar()读取的字符
	public static symbol sym;							//
	public static String id;	//当前的identifier
	public static int num;
	public static int floatNum;//
	public static int charPtr, line;							//getChar()计数器
	public static int machinePtr;								//虚拟机代码指针[0-MAX_CX]
	//public static char line[] = new char[81];			//
	public static char temp[] = new char[LEN_MAX + 1];	//暂时存符号的位置
	
	public static String word[] = new String[KEY_NUM];	//14个保留字
	public static symbol symWord[] = new symbol[KEY_ALL + 1];		//保留字对应的symbol
	public static symbol symChar[] = new symbol[256];			//单个char对应的symbol
	public static String charSym[] = new String[16];
	
	public String fileName;
	public static int err;
	
	public static Object table[][] = new Object[PTR_MAX][3];
	public static int tablePtr = 0;
	
	//public static int ptr = 0;
	
	public static void init() {
		for(int i = 0; i < 256; i++) {
			symChar[i] = symbol.SYM_NUL;
		}
		symChar['+'] = symbol.SYM_PLUS;
		symChar['-'] = symbol.SYM_MINUS;
		symChar['*'] = symbol.SYM_TIMES;
		symChar['/'] = symbol.SYM_SLASH;
		symChar['('] = symbol.SYM_LPAREN;
		symChar[')'] = symbol.SYM_RPAREN;
		symChar['='] = symbol.SYM_EQU;
		symChar[','] = symbol.SYM_COMMA;
		symChar['.'] = symbol.SYM_PERIOD;
		symChar['#'] = symbol.SYM_NEQ;
		symChar[';'] = symbol.SYM_SEMICOLON;
		
		word[0] = "begin";
		word[1] = "call";
		word[2] = "const";
		word[3] = "end";
		word[4] = "if";
		word[5] = "ood";
		word[6] = "procedure";
		word[7] = "then";
		word[8] = "var";
		word[9] = "while";
		word[10] = "read";
		word[11] = "write";
		word[12] = "repeat";
		word[13] = "until";
		/*for (int i = 0; i < 13; i++) {
			System.out.println(word[i]);
		}*/
		
		symWord[0] = symbol.SYM_BEGIN;
		symWord[1] = symbol.SYM_CALL;
		symWord[2] = symbol.SYM_CONST;
		symWord[3] = symbol.SYM_END;
		symWord[4] = symbol.SYM_IF;
		symWord[5] = symbol.SYM_OOD;
		symWord[6] = symbol.SYM_PROCEDURE;
		symWord[7] = symbol.SYM_THEN;
		symWord[8] = symbol.SYM_VAR;
		symWord[9] = symbol.SYM_WHILE;
		symWord[10] = symbol.SYM_READ;
		symWord[11] = symbol.SYM_WRITE;
		symWord[12] = symbol.SYM_REPEAT;
		symWord[13] = symbol.SYM_UNTIL;
		symWord[14] = symbol.SYM_DO;
		symWord[15] = symbol.SYM_ELSE;
		
		symWord[16] = symbol.SYM_IDENTIFIER;
		symWord[17] = symbol.SYM_NUMBER;
		
		symWord[18] = symbol.SYM_PLUS;
		symWord[19] = symbol.SYM_MINUS;
		symWord[20] = symbol.SYM_TIMES;
		symWord[21] = symbol.SYM_SLASH;
		symWord[22] = symbol.SYM_EQU;
		symWord[23] = symbol.SYM_NEQ;
		symWord[24] = symbol.SYM_LES;
		symWord[25] = symbol.SYM_LEQ;
		symWord[26] = symbol.SYM_GTR;
		symWord[27] = symbol.SYM_GEQ;
		symWord[28] = symbol.SYM_LPAREN;
		symWord[29] = symbol.SYM_RPAREN;
		symWord[30] = symbol.SYM_COMMA;
		symWord[31] = symbol.SYM_SEMICOLON;
		symWord[32] = symbol.SYM_PERIOD;
		symWord[33] = symbol.SYM_BECOMES;
		
		charSym[0] = "+";
		charSym[1] = "-";
		charSym[2] = "*";
		charSym[3] = "/";
		charSym[4] = "=";
		charSym[5] = "<>";
		charSym[6] = "<";
		charSym[7] = "<=";
		charSym[8] = ">";
		charSym[9] = ">=";
		charSym[10] = "(";
		charSym[11] = ")";
		charSym[12] = ",";
		charSym[13] = ";";
		charSym[14] = ".";
		charSym[15] = ":=";
		
		err = 0;
		charPtr = 0;
		line = 1;
		machinePtr = 0;
		ch = ' ';
		
		for (int i = 0; i < PTR_MAX; i++) {
			table[i][0] = new String();
			table[i][2] = new String();
		}
	}
	
	public static int getChar() {
		try {
			int tmp = InputListener.br.read();
			if (tmp != -1) {
				ch = (char) tmp;
				//System.out.print(ch);
				if(ch == '\n') {
					line++;
				}
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getSym() {
		int i, j, k;
		
		while (ch == ' ' || ch == 10 || ch == 9) {
			if (getChar() == -1)
				return -1;
		}
		
		if (ch >= 'a' && ch <= 'z')	{
			k = 0;
			do {//获取完整字符串
				if (k < LEN_MAX) {
					temp[k] = ch;
					k++;
				}
				if (getChar() == -1)
					return -1;
			} while (ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9');

			temp[k] = 0;
			id = new String();
			for (int x = 0; x < k; x++) {
				id += temp[x];
			}
			
			String str = id;
			int y;
			//System.out.println(id);
			for (y = 0; y < KEY_NUM; y++) {
				if(id.equals(word[y])) {
					sym = symWord[y];
					
					table[tablePtr][0] = new String(str);
					out((symbol)sym);
					break;
				}
			}
			if (y == KEY_NUM) {
				sym = symbol.SYM_IDENTIFIER;
				out(str);
			}
			
		} else {
			if (ch >= '0' && ch <= '9') {
				k = 0;
				num = 0;
				floatNum = 0;
				sym = symbol.SYM_NUMBER;
				while (ch >= '0' && ch <= '9') {//获取完整数字
					num = num * 10 + ch - '0';
					k++;
					if (getChar() == -1)
						return -1;
					if (ch >= 'a' && ch <= 'z') {
						JOptionPane.showMessageDialog(null, "Line " + line + ": 标识符非法");
						err++;
					}
				}
				if (ch == '.') {
					if (getChar() == -1)
						return -1;
					while (ch >= '0' && ch <= '9') {
						floatNum = floatNum * 10 + ch - '0';
						if (getChar() == -1)
							return -1;
					}
				}
				k--;
				if (k > NUM_MAX) {//数字过长
					JOptionPane.showMessageDialog(null, "Line " + line + ": 数字过长");
					err++;
				} else {
					if (floatNum ==0) {
						out(num);
					} else {
						out(num, floatNum);
					}
				}
			}else {//开始字符判断
				if (ch == ':') {
					if (getChar() == -1) {
						return -1;
					}
					
					if (ch == '=') {//:=
						sym = symbol.SYM_BECOMES;
						if (getChar() == -1) {
							return -1;
						}
					} else {//:
						sym = symbol.SYM_NUL;
					}
				} else {
					if (ch == '<') {
						if (getChar() == -1) {
							return -1;
						}
						if (ch == '=') {//<=
							sym = symbol.SYM_LEQ;
							if(getChar() == -1) {
								return -1;
							}
						} else if (ch == '>') {
							sym = symbol.SYM_NEQ;
						} else {//<
							sym = symbol.SYM_LES;
						}
					} else {
						if (ch == '>') {
							if (getChar() == -1) {
								return -1;
							}
							if (ch == '=') {//>=
								sym = symbol.SYM_GEQ;
								if (getChar() == -1) {
									return -1;
								}
							} else {//>
								sym = symbol.SYM_GTR;
							}
						} else {
							sym = symChar[ch];
							if (sym != symbol.SYM_PERIOD) {
								if (getChar() == -1) {
									return -1;
								}
							} else {
								return -1;
							}
						}
					}
				} 
				for (int x = 0; x <= 15; x++) {
					if (sym == symWord[x + 18]) {
						table[tablePtr][0] = charSym[x];
						break;
					}
				}
				out((symbol)sym);
			} 
		}
		return 0;
	}
	
	public static void out(symbol n) {
		for (int i = 0; i < KEY_ALL; i++) {
			if (symWord[i] == n) {
				table[tablePtr][1] = i;
				table[tablePtr][2] = new String("null");
				tablePtr++;
				return;
			}
		}
	}
	
	public static void out(String s) {
		table[tablePtr][0] = new String(s);
		table[tablePtr][1] = 16;
		table[tablePtr][2] = new String(s);
		tablePtr++;
	}

	public static void out(int n) {
		table[tablePtr][0] = n;
		table[tablePtr][1] = 17;
		table[tablePtr][2] = new String(Integer.toBinaryString(n));
		tablePtr++;
	}
	
	public static void out(int n, int f) {
		double d = f;
		String s = "";

		while (d > 1) {
			d /= 10;
		}
		//System.out.println(d);
		int count = 0;
		while (d > 0 && count < 10) {
			d *= 2;
			if (d > 1) {
				d -= 1;
				s += "1";
			} else {
				s += "0";
			}
			//System.out.println(s);
			count++;
		}
		//System.out.println(s);
		table[tablePtr][0] = new String(Integer.toString(n) + "." + Integer.toString(f));
		table[tablePtr][1] = 17;
		table[tablePtr][2] = new String(Integer.toBinaryString(n) + "." + s);
		//System.out.println(table[tablePtr][0]);
		//System.out.println(table[tablePtr][1]);
		//System.out.println(table[tablePtr][2]);
		tablePtr++;
	}
}
