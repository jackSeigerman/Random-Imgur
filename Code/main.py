import tkinter as gui
from tkinter import messagebox


# function called upon clicking the imgur button
def imgur():
    messagebox.showinfo("Info", "Button clicked!")


# function called upon clicking the gallery button
def gallery():
    messagebox.showinfo("Info", "Button clicked!")


# function called upon clicking the download button
def download():
    messagebox.showinfo("Info", "Button clicked!")


# main ui
root = gui.Tk()
root.title("Tkinter Example")
root.geometry("300x200")

# button ui
button1 = gui.Button(root, text="new imgur", command=imgur)
button2 = gui.Button(root, text="gallery", command=gallery)
button3 = gui.Button(root, text="download", command=download)

# button sizes
button1.pack(pady=20)
button2.pack(pady=20)
button3.pack(pady=20)

# main update loop
root.mainloop()
