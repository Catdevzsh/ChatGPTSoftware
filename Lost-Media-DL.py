import tkinter as tk
from tkinter import filedialog, scrolledtext
import subprocess
import threading
## @Proudly By Flames LLC 20XX [C] - AGI
# Function to download video
def download_video():
    # Get the URL, directory, and format from the user input
    url = url_entry.get()
    directory = dir_entry.get()
    format = format_var.get()

    # Set options for video, audio, and metadata based on user input
    options = ''
    if video_var.get():
        options += ' --write-video'  # This is just an example, not a valid youtube-dl option
    if audio_var.get():
        options += ' --extract-audio'  # Extract audio from the video
    if metadata_var.get():
        options += ' --write-info-json'  # Write video metadata to a JSON file
    
    # Construct the youtube-dl command
    command = f'youtube-dl -f {format} {options} -o "{directory}/%(title)s.%(ext)s" {url}'

    # Run the command in a new thread to prevent blocking the GUI
    threading.Thread(target=run_command, args=(command,)).start()

# Function to run a command and display the output in the GUI
def run_command(command):
    process = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    
    # Display the output of the command in the GUI
    for line in iter(process.stdout.readline, b''):
        console.insert(tk.END, line)
        console.see(tk.END)
    process.stdout.close()
    process.wait()

# Create the main window
root = tk.Tk()

# Create entry for URL
url_label = tk.Label(root, text="Video URL:")
url_label.pack()
url_entry = tk.Entry(root)
url_entry.pack()

# Create entry for directory
dir_label = tk.Label(root, text="Save directory:")
dir_label.pack()
dir_entry = tk.Entry(root)
dir_entry.pack()

# Create directory select button
def browse_directory():
    directory = filedialog.askdirectory()
    dir_entry.delete(0, tk.END)  # remove current text in entry
    dir_entry.insert(0, directory)  # insert the directory path

browse_button = tk.Button(root, text="Browse", command=browse_directory)
browse_button.pack()

# Create option for format
format_var = tk.StringVar(root)
format_var.set("mp4")  # default value
format_label = tk.Label(root, text="Video Format:")
format_label.pack()
format_option = tk.OptionMenu(root, format_var, "mp4", "webm", "mkv", "flv", "avi", "mov")
format_option.pack()

# Create options for video, audio, and metadata
video_var = tk.BooleanVar()
video_check = tk.Checkbutton(root, text="Download Video", variable=video_var)
video_check.pack()
audio_var = tk.BooleanVar()
audio_check = tk.Checkbutton(root, text="Extract Audio", variable=audio_var)
audio_check.pack()
metadata_var = tk.BooleanVar()
metadata_check = tk.Checkbutton(root, text="Write Metadata", variable=metadata_var)
metadata_check.pack()

# Create download button
download_button = tk.Button(root, text="Download", command=download_video)
download_button.pack()

# Create console output
console = scrolledtext.ScrolledText(root)
console.pack()

# Start the main event loop
root.mainloop()
