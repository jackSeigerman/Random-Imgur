import tkinter as tk
from tkinter import messagebox

def imgur():
    messagebox.showinfo("Info", "Button clicked!")

def gallery():
    messagebox.showinfo("Info", "Button clicked!")

def download():
    messagebox.showinfo("Info", "Button clicked!")

root = tk.Tk()
root.title("Tkinter Example")
root.geometry("300x200")

button1 = tk.Button(root, text="new imgur", command=imgur)
button2 = tk.Button(root, text="gallery", command=gallery)
button3 = tk.Button(root, text="download", command=download)

button1.pack(pady=20)
button2.pack(pady=20)
button3.pack(pady=20)

root.mainloop()
