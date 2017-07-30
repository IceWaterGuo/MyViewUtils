package com.itheima.myviewutils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewUtils {

	public static void inject(Activity activity){
		bindView(activity);
		bindClick(activity);
	}

	private static void bindView(Activity activity) {
		/**
		 * 1、获取activity的字节码
		 */
		Class<? extends Activity> clazz = activity.getClass();
		/**
		 * 2、获取字节码中的所有字段
		 */
		Field[] fields = clazz.getDeclaredFields();
		/**
		 * 3、遍历fields，找出字段上有注解的字段
		 */
		for (Field field : fields) {
			/**
			 *4、判断filed上有没有ViewInject注解 
			 */
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			/**
			 * 5、如果viewInject==null，则不用处理当前字段，否则需要处理
			 */
			if (viewInject!=null) {
				/**
				 * 6、获取ViewInject对象的值（resId）
				 */
				int resId = viewInject.value();
				/**
				 * 7、根据resId找到响应的控件
				 */
				View view = activity.findViewById(resId);
				/**
				 * 8、设置field可访问（暴力访问）
				 */
				field.setAccessible(true);
				/**
				 * 9、将控件view设置给field
				 */
				try {
					field.set(activity, view);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		
	}

	private static void bindClick(final Activity activity) {
		/**
		 * 1、获取对象的字节码
		 */
		Class<? extends Activity> clazz = activity.getClass();
		/**
		 * 2、获取字节码中的所有方法
		 */
		Method[] methods = clazz.getDeclaredMethods();
		/**
		 * 3、遍历method方法
		 */
		for (final Method method : methods) {
			/**
			 * 4、获取method方法上的OnClick注解
			 */
			OnClick onClick = method.getAnnotation(OnClick.class);
			/**
			 * 5、判断OnClick是否为空
			 */
			if (onClick!=null) {
				/**
				 * 6、获取OnClick对象的值resId
				 */
				int[] resIds = onClick.value();
				/**
				 * 7、遍历resIds
				 */
				for (int resId : resIds) {
					/**
					 * 8、从Activity身上找到对应的控件
					 */
					View view = activity.findViewById(resId);
					/**
					 * 9、给控件view绑定点击监听器
					 */
					view.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							/**
							 * 10、让method方法支持暴力反射
							 */
							method.setAccessible(true);
							/**
							 * 11、反射调用method方法
							 */
							try {
								method.invoke(activity, v);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		}
	}
}
