/**
 * 
 */
package com.example.com.suyi.phone.call;

import java.util.List;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.example.com.suyi.base.FaceCallBack;
import com.example.com.suyi.phone.call.app.CallApp;

//�����õ�λ�����ѹ��ܣ���Ҫimport����

/**
 * @author "suwg" 2016��5��30��
 */
public class BaiDuLocManger {

	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	

	public BaiDuLocManger() {
		onCreate();
	}

	public void onCreate() {
		mLocationClient = new LocationClient(CallApp.getInstance()); // ����LocationClient��
		mLocationClient.registerLocationListener(myListener); // ע���������
		initLocation();
	}

	public void end() {
		mLocationClient.stop();
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span = 1000;
		option.setScanSpan(span);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);// ��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(true);// ��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
		option.SetIgnoreCacheException(false);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
		mLocationClient.setLocOption(option);
	}

	FaceCallBack<String> mFaceCallBack;
	public void locationClientstart(FaceCallBack<String> mFaceCallBack) {
		this.mFaceCallBack=mFaceCallBack;
		mLocationClient.start();
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS��λ���
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// ��λ������ÿСʱ
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// ��λ����
				sb.append("\ndirection : ");
				sb.append(location.getDirection());// ��λ��
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps��λ�ɹ�");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ���綨λ���
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// ��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("���綨λ�ɹ�");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
				sb.append("\ndescribe : ");
				sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
			}
			sb.append("\nlocationdescribe : ");
			sb.append(location.getLocationDescribe());// λ�����廯��Ϣ
			List<Poi> list = location.getPoiList();// POI����
			if (list != null) {
				sb.append("\npoilist size = : ");
				sb.append(list.size());
				for (Poi p : list) {
					sb.append("\npoi= : ");
					sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
				}
			}
			Log.i("BaiduLocationApiDem", sb.toString());
			
			if(mFaceCallBack!=null)
			mFaceCallBack.callback(sb.toString());
		}
	}
}