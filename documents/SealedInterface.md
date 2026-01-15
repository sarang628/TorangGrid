# [Sealed Interface](https://kotlinlang.org/docs/sealed-classes.html)
- 클래스 계층에 통제된 상속(controlled inheritance)을 제공
- sealed class의 하위 클래스들은 컴파일 타임에 알 수 있다.
- 오직 같은 패키지에 있는 클래스만 상속이 가능하다.
- sealed interface도 동일하게 적용됨.
- 모듈이 한번 컴파일 되면 새로운 구현 클래스를 추가할 수 없다.
