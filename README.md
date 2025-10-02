# Android 도서 API

이 프로젝트는 [카카오 도서 API를 활용해 검색, 도서 상세, 즐겨찾기]를 구현한 안드로이드 애플리케이션입니다.  
Jetpack Compose와 MVI 아키텍처를 기반으로 제작되었습니다.

---

## 🚀 개발 환경 및 빌드 방법

1. **개발 환경**
    - Android Studio Narwhal Feature Drop | 2025.1.2
    - JDK 17(zulu-17)
    - Gradle 8.13

2. **빌드 방법**
   - 압축 해제 후 clean build

## 🛠 사용 기술 & 프레임워크

- 언어 : Kotlin
- UI : Jetpack Compose
- 아키텍처 : MVI + Clean Architecture
- DI : Hilt
- 비동기 : Kotlin Coroutines + Flow
- 로컬 DB : Room
- Gradle : Version Catalog + Convention Plugin
- 네트워크 : Retrofit2 + OkHttp
- 이미지 : Coil

## 📂 프로젝트 구조
```bash.
book/
┣ app/
┃ ┣ MainActivity.kt
┃ ┗ App.kt
┣ build-logic/   # version catalog convention plugin
┃ ┗ convention/  # convention plugin 및 앱 버전 정보
┣ data/          # repository, DataSource, DTO, Entity, Remote
┃ ┣ remote/      # DTO, DataSource
┃ ┣ local/       # Entity, DataSource
┃ ┗ repository/  # local, remote 브릿지 역할
┣ domain/        # UseCase, Domain 모델, repository interface
┣ feature/       # 각 화면별 presenter, viewmodel
┃ ┣ bookmark/    # 즐겨찾기 화면
┃ ┣ detail/      # 도서 상세 화면
┃ ┣ home/        # main back screen, bottom navigation
┃ ┣ search/      # 도서 검색 화면
┃ ┗ splash/      # splash 화면
┣ shared/        # 공통으로 사용할 수 있는 module 모음
┃ ┣ core-mvi/    # mvi reducer
┃ ┣ navigation/  # 각 화면별 navigation route Id
┃ ┣ ui/
┃ ┃ ┣ extension/ # Compose와 관련된 UI extension function
┃ ┃ ┗ system/    # Compose Ui Component 및 theme
┃ ┣ util/
┃ ┃ ┣ common/    # Log나 Crashlytics 같은 util helper
┃ ┃ ┗ extension/ # kotlin extension function
┣ build.gradle
┗ settings.gradle
```
## ✨ 주요 구현 포인트
1. MVI 패턴 기반 상태 관리
    - 이벤트(Event)를 통해 사용자의 이벤트를 기반으로 ViewModel에서 상태(State)를 관리하고 이펙트(Effect)로 화면상의 액션을 보여줌
    - 에러 및 성공 state를 명확히 처리
2. 페이징 처리
    - 라이브러리를 사용하지 않고, LaunchedEffect로 스크롤 위치에 따라 api를 호출하는 방식으로 개발
3. 디자인
   - Shimmer 효과를 통해 데이터 Loading 중 어색한 부분을 개선
   - home에서 backHandler 이벤트를 제어