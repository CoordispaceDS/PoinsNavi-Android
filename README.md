# PoinsNavi Library for Android

![Platform](https://img.shields.io/badge/Platform-Android-lightgrey.svg)
> PoinsNavi는 실내 길안내용 View 기반의 라이브러리 입니다.

![](https://dl.dropboxusercontent.com/s/gy879vgco3rqn0u/android_main.png)

# Contents
* [Requirements](#requirements)
* [Features](#features)
* [Permissions](#permissions)
* [Installation](#installation)
* [Usage](#usage)
  * [View 생성](#view-생성)
  * [지도보기 및 길찾기](#지도보기-및-길찾기)

# Requirements

* OS : Android 4.3 이상
* Sensor : Magnetic Field(Uncalibrated), Gyroscope(Uncalibrated), Accelerometer
* Device status : Location Information ON (in case above Android 6.0)

![](https://dl.dropboxusercontent.com/s/w4hezv4q6rpeodi/locationInfo.png)

# Features

* 위치 측위
* 경로 안내
* PoI 명칭 및 편의시설 검색

# Permissions

* Android 6.0 이상의 OS 인 경우 위치 정보 사용 Permission 동의를 받아야 합니다. 
```Java
protected void onCreate(Bundle savedInstanceState) {
	…
    // 위치 정보를 사용하기 위한 사용자 응답 
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSIONS_LOCATION);
        }
    }
    …
}
```

* 위치 정보 사용을 동의하거나 동의하지 않은 경우의 처리 
```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    switch(requestCode) {
        case REQUEST_CODE_PERMISSIONS_LOCATION:
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();    // location 권한을 거부한 경우
            }
            break;
        default:
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
```

# Installation

Android Studio Application 에서 PoinsNavi를 사용하기 위해서는 다음 순서로 포팅을 합니다. 
1. PoinsNavi.aar 을 Project의 library패스로 copy 
2. Project의 Build.gradle 파일 Open 
3. dependencies 및 repository 를 다음과 같이 추가 
```gradle
repositories {
    flatDir {
        dirs '../'		// aar file의 경로 
    }
}

dependencies {
    …
    compile(name: 'PoinsNavi', ext: 'aar')
}
```

# Usage

### View 생성

__Framgment 호출__

PoinsNavi Fragment를 호출하여 맵 화면을 볼 수 있습니다.
지도를 일부 layout 내에 내장하고 지도 이외의 기능을 유연하게 사용하고자 할 경우 아래와 같이 Fragment를 추가합니다. 
1. Layout 생성

activity.xml
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent" android:layout_height="match_parent"
    tools:context="com.coordisapce.navigationsample.NavigationActivity">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/ll_fragment">
    </FrameLayout>

</RelativeLayout>
```

2. 소스코드 추가

```java
int mapIndex = 1;
IndoorFragment indoorFragment = IndoorFragment.newInstance(mapIndex);
FragmentManager fragmentManager = getFragmentManager();
Fragment f = fragmentManager.findFragmentById(R.id.ll_fragment);
if (f != null && f instanceof IndoorFragment) {
  fragmentManager.beginTransaction().add(R.id.ll_fragment, indoorFragment).commit();
}
fragmentManager.beginTransaction().addToBackStack(null);
```

여러건물을 함께 사용할 경우 다음과 같이 ArrayList로 값을 넘겨 줄 수 있습니다.
```java
ArrayList<Integer> mapIds = new ArrayList<>();
mapIds.add(1);
mapIds.add(2);
mapIds.add(3);
IndoorFragment indoorFragment = IndoorFragment.newInstance(mapIds);
FragmentManager fragmentManager = getFragmentManager();
Fragment f = fragmentManager.findFragmentById(R.id.ll_fragment);
if (f != null && f instanceof IndoorFragment) {
  fragmentManager.beginTransaction().add(R.id.ll_fragment, indoorFragment).commit();
}
fragmentManager.beginTransaction().addToBackStack(null);
```

Fragment의 경우 외부에서 여러 가지 설정을 할 수 있으며 자세한 API는 [IndoorFragment API](#indoorfragment-api) 에서 확인할 수 있습니다. 

__Activity 호출__

간단히 PoinsNavi 자체 Activity를 사용할 경우 아래와 같이 Intent 호출도 가능합니다.
```java
Intent intent = new Intent(this, IntroActivity.class);
intent.putExtra(IntroActivity.KEY_MAPINDEX, mapIndex);
startActivity(intent);
```

여러건물을 함께 사용할 경우 다음과 같이 ArrayList로 값을 넘겨 줄 수 있습니다.
```java
ArrayList<Integer> mapIds = new ArrayList<>();
mapIds.add(1);
mapIds.add(2);
mapIds.add(3);
mSelectedMapIndexList = mapIds;

Intent intent = new Intent(MainActivity.this, IntroActivity.class);
intent.putExtra(IntroActivity.KEY_MAPINDEX_LIST, mapIds);
startActivity(intent);
```

### 지도보기 및 길찾기

다음 method를 통해 매장 바로가기 및 길찾기 기능을 사용할 수 있습니다.
```java
void findPosition(int index, boolean guide) 
void findPosition(String name, boolean guide) 
```
1. 매장의 room index를 아는 경우

* 지도보기
```java
indoorFragment.findPosition(6494, false);
```
* 길찾기
```java
indoorFragment.findPosition(6494, true);
```

2. 매장명(POI명)을 아는 경우
동일한 매장명을 사용하는 경우 일치하는 첫번째 검색 결과를 사용합니다.

* 지도보기
```java
indoorFragment.findPosition("코스메데코르테", false);
```

* 길찾기
```java
indoorFragment.findPosition("코스메데코르데", true);
```

* _맵의 크기에 따라 로딩시간이 오래 걸릴 수 있어 맵과의 전환이 잦은 시나리오를 사용할 경우 맵뷰 위에 UI를 작성한 후 View전환을 하는 형태로 개발하는 것을 권장합니다._
