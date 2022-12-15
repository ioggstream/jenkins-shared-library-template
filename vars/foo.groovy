import org.foo.Bar

def call() {
    String name = libraryResource "org/foo/bar.json"
    echo "Hello, ${name}!"
}
