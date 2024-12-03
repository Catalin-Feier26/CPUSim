package lowComponents;
import model.AluOperation;
import model.ext;
/**
 * This class represents the Control Unit of the MIPS architecture
 * It contains the control signals for the datapath
 * @see AluOperation
 */
public class ControlUnit {
    private ext extension;
    private boolean pcSrc;
    private boolean regDst;
    private boolean branch;
    private boolean memRead;
    private boolean memToReg;
    private AluOperation aluOp;  // Use the AluOperation enum
    private boolean memWrite;
    private boolean aluSrc;
    private boolean regWrite;
    private boolean jump;
    private boolean hiWrite;
    private boolean loWrite;
    private boolean hiSrc;
    private boolean loSrc;
    /**
     * Constructor for the ControlUnit class.
     * @param instruction The instruction based on which the control signals are generated.
     */
    public ControlUnit(String instruction){
        generateControlSignals(instruction);
    }
    /**
     * Constructor for the ControlUnit class.
     * Initializes all control signals to false.
     */
    public ControlUnit() {
        reset();
    }

    /**
     * Method to generate and set all Control Signals based on the instruction.
     * @param instruction - The instruction based on which the control signals are set.
     */
    public void generateControlSignals(String instruction){
        String[] parts = instruction.toUpperCase().split(" ");
        switch (parts[0]) {
            case "NOP":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "ADD":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "SUB":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.SUB;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "AND":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.AND;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "OR":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.OR;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "NAND":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.NAND;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "XOR":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.XOR;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "NOR":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.NOR;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "MULT":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.MULT;
                aluSrc = false;
                hiWrite = true;
                loWrite = true;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "DIV":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.DIV;
                aluSrc = false;
                hiWrite = true;
                loWrite = true;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "MFHI":
                regWrite = true;
                regDst=true;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = true;
                jump = false;
                hiSrc=true;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "MFLO":
                regWrite = true;
                regDst=true;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = true;
                jump = false;
                hiSrc=false;
                loSrc=true;
                extension = ext.NO_EXTENSION;
                break;
            case "MOV":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "SLL":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.SL;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.ZERO_EXTENSION;
                break;
            case "SRL":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.SR;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.ZERO_EXTENSION;
                break;
            case "SLLV":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.SL;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "SRLV":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.SR;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "SLT":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.SLT;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "JR":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = true;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = true;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "MTHI":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = true;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "MTLO":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = false;
                loWrite = true;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "SRA":
                regWrite = true;
                regDst = true;
                aluOp = AluOperation.SRA;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension = ext.NO_EXTENSION;
                break;
            case "ADDI":
                regWrite = true;
                regDst = false;
                aluOp = AluOperation.ADD;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.SIGN_EXTENSION;
                break;
            case "SUBI":
                regWrite = true;
                regDst = false;
                aluOp = AluOperation.SUB;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.SIGN_EXTENSION;
                break;
            case "ANDI":
                regWrite = true;
                regDst = false;
                aluOp = AluOperation.AND;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.ZERO_EXTENSION;
                break;
            case "ORI":
                regWrite = true;
                regDst = false;
                aluOp = AluOperation.OR;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.ZERO_EXTENSION;
                break;
            case "XORI":
                regWrite = true;
                regDst = false;
                aluOp = AluOperation.XOR;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.ZERO_EXTENSION;
                break;
            case "LW":
                regWrite = true;
                regDst = false;
                aluOp = AluOperation.ADD;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = true;
                memToReg = true;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.SIGN_EXTENSION;
                break;
            case "SW":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.ADD;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = true;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.SIGN_EXTENSION;
                break;
            case "BEQ":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.SUB;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = true;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.SIGN_EXTENSION;
                break;
            case "BNE":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.SUB;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = true;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.SIGN_EXTENSION;
                break;
            case "BGEZ":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.SLT;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = true;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.SIGN_EXTENSION;
                break;
            case "BLTZ":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.SLT;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = true;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.SIGN_EXTENSION;
                break;
            case "SLTI":
                regWrite = true;
                regDst = false;
                aluOp = AluOperation.SLT;
                aluSrc = true;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = false;
                hiSrc=false;
                loSrc=false;
                extension=ext.SIGN_EXTENSION;
                break;
            case "J":
                regWrite = false;
                regDst = false;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = true;
                hiSrc=false;
                loSrc=false;
                extension=ext.NO_EXTENSION;
                break;
            case "JAL":
                regWrite = true;
                regDst = false;
                aluOp = AluOperation.ADD;
                aluSrc = false;
                hiWrite = false;
                loWrite = false;
                pcSrc = false;
                branch = false;
                memWrite = false;
                memRead = false;
                memToReg = false;
                jump = true;
                hiSrc=false;
                loSrc=false;
                extension=ext.NO_EXTENSION;
                break;
            default:
                System.out.println("Invalid instruction " + parts[0]);
                break;
        }
    }
    /**
     * Resets the content of the Control Unit to false.
     * This method is called by the constructor.
     * @see ControlUnit#ControlUnit()
     */
    public void reset(){
        extension = ext.SIGN_EXTENSION;
        pcSrc = false;
        regDst = false;
        branch = false;
        memRead = false;
        memToReg = false;
        aluOp = AluOperation.UNSPECIFIED;
        memWrite = false;
        aluSrc = false;
        regWrite = false;
        jump = false;
        hiWrite = false;
        loWrite = false;
    }
    /**
     * Gets the value of the PcSrc control signal.
     * @return boolean The value of the PcSrc control signal.
     */
    public boolean getPcSrc() {
        return pcSrc;
    }
    /**
     * Sets the value of the PcSrc control signal.
     * @param pcSrc The value to be set.
     */
    public void setPcSrc(boolean pcSrc) {
        this.pcSrc = pcSrc;
    }
    /**
     * Gets the value of the RegDst control signal.
     * @return boolean The value of the RegDst control signal.
     */
    public boolean getRegDst() {
        return regDst;
    }
    /**
     * Sets the value of the RegDst control signal.
     * @param regDst The value to be set.
     */
    public void setRegDst(boolean regDst) {
        this.regDst = regDst;
    }
    /**
     * Gets the value of the Branch control signal.
     * @return boolean The value of the Branch control signal.
     */
    public boolean getBranch() {
        return branch;
    }
    /**
     * Sets the value of the Branch control signal.
     * @param branch The value to be set.
     */
    public void setBranch(boolean branch) {
        this.branch = branch;
    }
    /**
     * Gets the value of the MemRead control signal.
     * @return boolean The value of the MemRead control signal.
     */
    public boolean getMemRead() {
        return memRead;
    }
    /**
     * Sets the value of the MemRead control signal.
     * @param memRead The value to be set.
     */
    public void setMemRead(boolean memRead) {
        this.memRead = memRead;
    }
    /**
     * Gets the value of the MemToReg control signal.
     * @return boolean The value of the MemToReg control signal.
     */
    public boolean getMemToReg() {
        return memToReg;
    }
    /**
     * Sets the value of the MemToReg control signal.
     * @param memToReg The value to be set.
     */
    public void setMemToReg(boolean memToReg) {
        this.memToReg = memToReg;
    }
    /**
     * Gets the value of the AluOp control signal.
     * @return AluOperation The value of the AluOp control signal.
     */
    public AluOperation getAluOp() {
        return aluOp;
    }
    /**
     * Sets the value of the AluOp control signal.
     * @param aluOp The value to be set.
     */
    public void setAluOp(AluOperation aluOp) {
        this.aluOp = aluOp;
    }
    /**
     * Gets the value of the MemWrite control signal.
     * @return boolean The value of the MemWrite control signal.
     */
    public boolean getMemWrite() {
        return memWrite;
    }
    /**
     * Sets the value of the MemWrite control signal.
     * @param memWrite The value to be set.
     */
    public void setMemWrite(boolean memWrite) {
        this.memWrite = memWrite;
    }
    /**
     * Gets the value of the AluSrc control signal.
     * @return boolean The value of the AluSrc control signal.
     */
    public boolean getAluSrc() {
        return aluSrc;
    }
    /**
     * Sets the value of the AluSrc control signal.
     * @param aluSrc The value to be set.
     */
    public void setAluSrc(boolean aluSrc) {
        this.aluSrc = aluSrc;
    }
    /**
     * Gets the value of the RegWrite control signal.
     * @return boolean The value of the RegWrite control signal.
     */
    public boolean getRegWrite() {
        return regWrite;
    }
    /**
     * Sets the value of the RegWrite control signal.
     * @param regWrite The value to be set.
     */
    public void setRegWrite(boolean regWrite) {
        this.regWrite = regWrite;
    }
    /**
     * Gets the value of the Jump control signal.
     * @return boolean The value of the Jump control signal.
     */
    public boolean getJump() {
        return jump;
    }
    /**
     * Sets the value of the Jump control signal.
     * @param jump '1' if we have a jump instruction, 0 if not
     */
    public void setJump(boolean jump) {
        this.jump = jump;
    }
    /**
     * Gets the value of the HiWrite control signal.
     * @return boolean The value of the HiWrite control signal.
     */
    public boolean getHiWrite() {
        return hiWrite;
    }
    /**
     * Sets the value of the HiWrite control signal.
     * @param hiWrite Should be 1 if the operation modifies the Hi register
     */
    public void setHiWrite(boolean hiWrite) {
        this.hiWrite = hiWrite;
    }
    /**
     * Gets the value of the LoWrite control signal.
     * @return boolean The value of the LoWrite control signal.
     */
    public boolean getLoWrite() {
        return loWrite;
    }
    /**
     *  Sets the value of the LoWrite control signal.
     * @param loWrite should be 1 if we modify the Lo register and 0 if not.
     */
    public void setLoWrite(boolean loWrite) {
        this.loWrite = loWrite;
    }
    /**
     * Setter for the hiSrc signal.
     * @param hiSrc
     */
    public void setHiSrc(boolean hiSrc) {
        this.hiSrc = hiSrc;
    }
    /**
     * Setter for the loSrc
     * @param loSrc  The value to set the LoSrc control signal
     */
    public void setLoSrc(boolean loSrc) {
        this.loSrc = loSrc;
    }
    /**
     * Getter for the HiSrc control signals
     * @return boolean
     */
    public boolean getHiSrc(){
        return  hiSrc;
    }
    /**
     * Getter for the LoSrc control signal.
     * @return boolean
     */
    public boolean getLoSrc(){
        return loSrc;
    }
    /**
     * Gets the value of the extension control signal.
     * @return ext The value of the extension control signal.
     */
    public ext getExtension() {
        return extension;
    }
    /**
     * Sets the value of the extension control signal.
     * @param extension The value to be set.
     */
    public void setExtension(ext extension) {
        this.extension = extension;
    }
    /**
     * Override for the general toString method
     * @return String - The formatted string of the class.
     */
    @Override
    public String toString(){
        return "ControlUnit{" +
                "extension=" + extension +
                ", pcSrc=" + pcSrc +
                ", regDst=" + regDst +
                ", branch=" + branch +
                ", memRead=" + memRead +
                ", memToReg=" + memToReg +
                ", aluOp=" + aluOp +
                ", memWrite=" + memWrite +
                ", aluSrc=" + aluSrc +
                ", regWrite=" + regWrite +
                ", jump=" + jump +
                ", hiWrite=" + hiWrite +
                ", loWrite=" + loWrite +
                '}';
    }
}
