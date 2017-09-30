#!/usr/bin/env python

max_freq = 0

class Entry:
    def __init__(self, seq):
        self.seq = seq
        self.symbols = []
        self.meanings = []
        self.freq = None

    def print(self):
        if self.freq is None or len(self.symbols) == 0 or len(self.meanings) == 0:
            return

        print("E")
        print("F:" + str(self.freq))
        for s in self.symbols:
            print("S:" + s)
        for m in self.meanings:
            print("T:" + m)

def parse_entry(ele):
    global max_freq
    seq = ele.find('ent_seq').text
    e = Entry(seq)
    for child in ele:
        if child.tag == "k_ele":
            for child2 in child:
                if child2.tag == "keb":
                    e.symbols.append(child2.text)
                if child2.tag == "ke_pri":
                    t = child2.text
                    if t.startswith("nf"):
                        e.freq = int(t[2:])
        elif child.tag == "sense":
            for child2 in child:
                if child2.tag == "gloss":
                    e.meanings.append(child2.text)
    if e.freq:
        if e.freq > max_freq:
            max_freq = e.freq
        e.print()


import xml.etree.ElementTree as ET
tree = ET.parse('JMdict_e')
root = tree.getroot()
for entry in root:
    if entry.tag == "entry":
        parse_entry(entry)

#print(max_freq)
