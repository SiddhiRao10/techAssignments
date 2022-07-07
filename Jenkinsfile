pipeline {
   agent any
   stages {
      stage('setup') {
         steps {
            browserstack(credentialsId: 'd7bc1b99-2a8d-44fe-b6c7-738e747b9c52') {
               echo "hello"
            }
            browserstack(credentialsId: 'd7bc1b99-2a8d-44fe-b6c7-738e747b9c52', localConfig: [localOptions: '<local-options>', localPath: '/path/to/local']) {
                // commands for executing tests
                 sh 'wget "https://www.browserstack.com/browserstack-local/BrowserStackLocal-linux-x64.zip"'
                                 sh 'unzip BrowserStackLocal-linux-x64.zip'
                                 sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon start'
                                 sh '<your_test_commands>'
                                 sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon stop'

                                 // For macOS-based systems, add the following commands in the given console to download the binary, run it, and stop its execution after the test has been executed.
                                 sh 'wget "https://www.browserstack.com/browserstack-local/BrowserStackLocal-darwin-x64.zip"'
                                 sh 'unzip BrowserStackLocal-darwin-x64.zip'
                                 sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon start'
                                 sh '<your_test_commands>'
                                 sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon stop'

                                 // For Windows-based systems, add the following commands in the given console to download the binary, run it, and stop its execution after the test has been executed.
                                 sh 'wget "https://www.browserstack.com/browserstack-local/BrowserStackLocal-win32.zip"'
                                 sh 'powershell.exe D:\BrowserStackLocal.exe'
                                 sh '<your-test-commands>'
                                 sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon stop'

            }

         }
      }
    }
  }
