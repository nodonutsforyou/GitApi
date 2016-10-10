Test framework for assignment

purpose:
to test create and delete github api endpoints

how to use:
edit config file (src\main\resources\testData.api.github.com.xml) - need to specify login, username, password fields with valid user info
Important - de safe with password. While sending password is encrypted, but password is not encripted on local computer. So keep track, of where yopu store config file with password and don't forget to delete password after you finish.

to run use maven surefire:test goal
!!!IMPORTANT!!! /*TODO*/ if used several times github will treat you as robot, and hide your repository from public. If you several times in a row - I can block your IP.
So don't use it more then once.

maven allows to set 1 important parameter base.testSuite
default value is smoke.xml, you can change it to pairwize.xml
parameter base.testSuite states wich test suite will be used.
smoke.xml is used to quick smoke test - it creates repository and deletes it.
pairwize.xml is used to make full testing - 24 positive test cases and 5 negative test cases and then deletes all crated repositories

Brightpoints:
- maven allows to set different variables, as api basse url, thread count, and test scenario
- xml config file allows allows quick set up and easy of change environment. If you want to test different environment you can change url parameter and create new configuration xml file. Each host can have there own xml file, and have different auth parameters
- by default test performs quick smoke test, just to check if api is working. If you need more complex test - you can easily change parameters, for example in jenkins.
- Full set of tests uses pairwize methodology. If we have full set of all posible tests with create api endpoint, it will take greate amount of time. So we test only unique pairs of parameters in endpoint.
- we create special object - Repo - to store info on created object. So if we can have access to backend, we could test if it is really created as per specification.

 Assumptions/omissions/todo's
- Havn't realy checked outcome of requests. In good framework I should have created special object to deserialize JSON reply, and validate it by initial objects. But I don't have specifications to check it correct way, and don't have backend access to check if objects was realy created
- TODO delete negative test. It will be easy test with just an rquest to delete unexisting repository
- TODO check of object exists. It could be many different ways to doo that. Exact way depends of level of testing. It should be beckend test for unit test, or API requests for end-to-end tests
- I don't have account to check private repositories - I have just skipped them.