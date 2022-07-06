pipeline {
   agent any
   stages {
      stage('setup') {
         steps {
            browserstack(credentialsId: '7cb321ca-3f19-45a1-90ef-b804b630dcba') {
               echo "hello"
            }
            browserstack(credentialsId: '7cb321ca-3f19-45a1-90ef-b804b630dcba', localConfig: [localOptions: '<local-options>', localPath: '/path/to/local']) {
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
