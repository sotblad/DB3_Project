import subprocess

p = subprocess.Popen(['pgrep', '-f', 'tt.py'], stdout=subprocess.PIPE)
out, err = p.communicate()

while(true):
    print("")

print(out)
