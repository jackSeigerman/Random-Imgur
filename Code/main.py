import tkinter as gui
from tkinter import messagebox

def imgur():
    messagebox.showinfo("Info", "Button clicked!")

def gallery():
    messagebox.showinfo("Info", "Button clicked!")

def download():
    messagebox.showinfo("Info", "Button clicked!")

root = gui.Tk()
root.title("Tkinter Example")
root.geometry("300x200")

button1 = gui.Button(root, text="new imgur", command=imgur)
button2 = gui.Button(root, text="gallery", command=gallery)
button3 = gui.Button(root, text="download", command=download)

button1.pack(pady=20)
button2.pack(pady=20)
button3.pack(pady=20)

root.mainloop()
