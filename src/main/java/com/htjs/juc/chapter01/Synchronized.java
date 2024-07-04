package com.htjs.juc.chapter01;

/**
 * 同步代码块和同步方法
 * javap -v -p Synchronized.class
 * {
 *   public com.htjs.juc.chapter01.Synchronized();
 *     descriptor: ()V
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=1, locals=1, args_size=1
 *          0: aload_0
 *          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *          4: return
 *       LineNumberTable:
 *         line 7: 0
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       5     0  this   Lcom/htjs/juc/chapter01/Synchronized;
 *
 *   public static void main(java.lang.String[]);
 *     descriptor: ([Ljava/lang/String;)V
 *     flags: ACC_PUBLIC, ACC_STATIC
 *     Code:
 *       stack=2, locals=3, args_size=1
 *          0: ldc           #2                  // class com/htjs/juc/chapter01/Synchronized
 *          2: dup
 *          3: astore_1
 *          4: monitorenter 监控器进入获得锁
 *          5: aload_1
 *          6: monitorexit  监控器退出，释放锁
 *          7: goto          15
 *         10: astore_2
 *         11: aload_1
 *         12: monitorexit
 *         13: aload_2
 *         14: athrow
 *         15: invokestatic  #3                  // Method synchronizedMethod:()V
 *         18: return
 *       Exception table:
 *          from    to  target type
 *              5     7    10   any
 *             10    13    10   any
 *       LineNumberTable:
 *         line 10: 0
 *         line 12: 5
 *         line 13: 15
 *         line 14: 18
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0      19     0  args   [Ljava/lang/String;
 *       StackMapTable: number_of_entries = 2
 *         frame_type = 255 // full_frame //
 *           offset_delta = 10
 *           locals = [ class "[Ljava/lang/String;", class java/lang/Object ]
 *           stack = [ class java/lang/Throwable ]
 *         frame_type = 250 // chop //
 *           offset_delta = 4
 *
 *   public static synchronized void synchronizedMethod(); //方法修饰符 public static synchronized
 *     descriptor: ()V
 *     flags: ACC_PRIVATE, ACC_STATIC, ACC_SYNCHRONIZED
 *     Code:
 *       stack=0, locals=0, args_size=0
 *          0: return
 *       LineNumberTable:
 *         line 17: 0
 * }
 */

/**
 *
 */
public class Synchronized {


    public static void main(String[] args) {
        synchronized(Synchronized.class) {

        }
        synchronizedMethod();
    }

    public static synchronized void  synchronizedMethod() {
    }
}
